package com.tjgs.robotevolution.level;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tjgs.robotevolution.Game;
import com.tjgs.robotevolution.JsonSerializationHandler;
import com.tjgs.robotevolution.collision.EntityCollisionSolver;
import com.tjgs.robotevolution.collision.MapCollisionSolver;
import com.tjgs.robotevolution.components.ColliderComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.components.GraphicsComponent;
import com.tjgs.robotevolution.components.PlayerControllerComponent;
import com.tjgs.robotevolution.components.PositionComponent;
import com.tjgs.robotevolution.entity.Entity;
import com.tjgs.robotevolution.entity.EntityBuilder;
import com.tjgs.robotevolution.graphics.Camera;
import com.tjgs.robotevolution.graphics.GraphicsRenderer;
import com.tjgs.robotevolution.graphics.MapRenderer;
import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.map.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class Level {

    public static final String TAG = Level.class.getSimpleName();

    private Game game;

    private Camera camera;

    private Map map;
    private MapCollisionSolver mapCollider;
    private MapRenderer mapRenderer;

    private HashMap<Integer, Entity> entities;
    private EntityCollisionSolver entityCollider;
    private GraphicsRenderer entityRenderer;

    private ArrayList<Integer> removeList;

    private String name;

    private Gson gson;

    /**
     * Use this Constructor when the level needs to be loaded from a file
     * @param game main game object
     * @param name name of the level
     */
    public Level(Game game, String name){
        this.game = game;
        this.name = name;

        createGson();

        LevelBuilder builder = loadLevel(name);

        init(builder);

    }

    /**
     * Use this constructor when programmatically creating a level
     * @param builder builder for the level
     * @param name name of level for loading/saving
     */
    public Level(Game game, LevelBuilder builder, String name){
        this.game = game;
        this.name = name;

        createGson();
        init(builder);
    }

    private void init(LevelBuilder builder){
        this.map = builder.map;
        mapCollider = new MapCollisionSolver(builder.getMap());
        entityCollider = new EntityCollisionSolver();

        entityRenderer = new GraphicsRenderer();
        mapRenderer = new MapRenderer(map);

        this.camera = new Camera(builder.camera);
        createEntites(builder.entityBuilders);

        removeList = new ArrayList<>();
    }

    private void createGson(){
        GsonBuilder gBuilder = new GsonBuilder();
        gBuilder.setPrettyPrinting();
        gBuilder.excludeFieldsWithoutExposeAnnotation();
        gBuilder.registerTypeAdapter(ComponentModel.class, new JsonSerializationHandler());
        gson = gBuilder.create();
    }

    private void createEntites(ArrayList<EntityBuilder> builders){

        entities = new HashMap<>();

        for(EntityBuilder builder: builders){

            Entity entity = builder.build(this);
            this.addEntity(entity);

            if(entity.hasComponent(ComponentType.MAP_COLLIDER)) {
                mapCollider.addColliderComponent((ColliderComponent) entity.getComponent(ComponentType.MAP_COLLIDER));
                entityCollider.addColliderComponent((ColliderComponent) entity.getComponent(ComponentType.MAP_COLLIDER), entity.getId());
            }
            if(entity.hasComponent(ComponentType.GRAPHICS)) {
                entityRenderer.addGraphics((GraphicsComponent) entity.getComponent(ComponentType.GRAPHICS));
            }
            //TODO: create cleaner/more robust method of doing this
            //add player controller if the isPlayerControlled flag is set in the builder
            if(builder.id <= 1){
                PlayerControllerComponent controller = new PlayerControllerComponent();
                entity.addComponent(controller);
                entity.linkComponents();
                game.addInputListener(controller);
            }

            if(entity.getId() == camera.getCameraFollowId()) {
                camera.setFollowPosition((PositionComponent) entity.getComponent(ComponentType.POSITION), entity.getId());
            }
        }
    }

    public void update(float dt){
        for(Entity entity: entities.values()){
            entity.update(dt);
        }
        mapCollider.step(dt);
        entityCollider.step(dt);

        for(Integer i: removeList) {
            Log.d(TAG, "update: remove Entity " + i);
            Entity e = entities.get(i);

            if(e.hasComponent(ComponentType.GRAPHICS)){
               entityRenderer.removeGraphics((GraphicsComponent) e.getComponent(ComponentType.GRAPHICS));
            }

            if(e.hasComponent(ComponentType.MAP_COLLIDER)){
                entityCollider.removeColliderComponent((ColliderComponent) e.getComponent(ComponentType.MAP_COLLIDER));
                mapCollider.removeColliderComponent((ColliderComponent) e.getComponent(ComponentType.MAP_COLLIDER));
            }
            entities.remove(i);
            Log.d(TAG, "update: entity removed " + i);
        }

        //Log.d(TAG, "update: done");
        removeList.clear();

        camera.update(dt);
    }

    public void render(SpriteBatch batch){
        mapRenderer.draw(batch, camera);
        entityRenderer.draw(batch, camera);
    }

    public Camera getCamera(){
        return camera;
    }

    public Map getMap(){
        return map;
    }

    public Entity getEntity(int id){
        return entities.get(id);
    }

    public Collection<Entity> getEntities(){
        return entities.values();
    }

    public void addEntity(Entity entity){
        if(entities.containsKey(entity.getId())){
            throw new RuntimeException("Duplicate Entity ID: " + entity.getId());
        }
        entities.put(entity.getId(), entity);
    }

    public void removeEntity(Entity entity){
        removeList.add(entity.getId());
    }

    public void removeEntity(int id){ removeList.add(id); }

    public String getName(){
        return name;
    }

    public void saveLevel(){

        LevelBuilder builder = new LevelBuilder();
        builder.map = this.map;
        builder.camera = this.camera.getCameraModel();

        for(Entity e: entities.values()){
            builder.addEntityBuilder(e.getEntityBuilder());
        }

        String jsonOut = gson.toJson(builder);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RobotEvolution");
        myDir.mkdirs();

        String fname = name + ".lvl";
        File file = new File (myDir, fname);

        if (file.exists ()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(jsonOut.getBytes());
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Level saved with Name: " + fname);

    }

    public LevelBuilder loadLevel(String name){
        int id = Game.context.getResources().getIdentifier(name, "raw", Game.context.getPackageName());

        InputStream is = Game.context.getResources().openRawResource(id);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();
            while (line != null){
                stringBuilder.append(line);
                stringBuilder.append('\n');
                line = reader.readLine();
            }

            reader.close();
            is.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        String jsonData = stringBuilder.toString();

        return gson.fromJson(jsonData, LevelBuilder.class);
    }

}
