package com.tjgs.robotevolution;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import com.tjgs.robotevolution.entity.EntityBuilder;
import com.tjgs.robotevolution.entity.EntityFactory;
import com.tjgs.robotevolution.graphics.BatchRenderer;
import com.tjgs.robotevolution.graphics.Camera;
import com.tjgs.robotevolution.graphics.CameraModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.Texture;
import com.tjgs.robotevolution.graphics.TileSet;
import com.tjgs.robotevolution.input.InputListener;
import com.tjgs.robotevolution.input.UtilInputListener;
import com.tjgs.robotevolution.level.Level;
import com.tjgs.robotevolution.level.LevelBuilder;
import com.tjgs.robotevolution.manager.TextureManager;
import com.tjgs.robotevolution.map.Map;
import com.tjgs.robotevolution.map.MapUtils;

import java.util.ArrayList;

public class Game implements InputListener{
	
	public static final String TAG = Game.class.getSimpleName();

    public static Context context = null;

	private int screenWidth;
	private int screenHeight;

    private Level level;

    private SpriteBatch batch;

    private Texture texture;

    private ArrayList<InputListener> inputListeners;

	public Game(Context context){
        this.context = context;

        //initialize open gl common stuff here
        GLES20.glDisable(GLES10.GL_LIGHTING);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glClearColor(0f, 0f, 1f, 1f);
	}
	
	public void update(float dt){
        level.update(dt);

	}
	
	public void render(){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        level.render(batch);

        batch.render();
        texture.unBind();
        GLES20.glFlush();
	}

    /**
     * Called when screen size changes
     * @param width halfWidth of screen
     * @param height halfHeight of screen
     */
	public void screenSizeChanged(int width, int height){
		//reset screen variables
        screenWidth = width;
		screenHeight = height;

        Log.d(TAG, "Screen Size Changed: " + width + "x" + height);
        GLES20.glViewport(0, 0, width, height);

        inputListeners = new ArrayList<>();

        /*********** LEVEL STUFF **********/
        String levelName = "testlevel1";

        //loadMapFromFile(levelName);
        createMap(levelName);

        batch = new SpriteBatch(level.getCamera(), new BatchRenderer());
        texture = TextureManager.getTexture("texture1");

        //add Utility input listener
        inputListeners.add(new UtilInputListener(this, level));
	}

    public void loadMapFromFile(String name){
        level = new Level(this, name);
    }

    public void createMap(String name){
        //create and link map stuff
        LevelBuilder levelBuilder = new LevelBuilder();

        generateMap(levelBuilder);

        //create and link camera
        Camera camera = new Camera(getScreenWidth(), getScreenHeight());
        camera.setTarget(camera.getX() + 16f, camera.getY() + 12f);

        CameraModel camModel = new CameraModel();
        camModel.setPosition(16, 40);
        camModel.setTarget(16, 12);

        levelBuilder.setCamera(camModel);

        levelBuilder.addEntityBuilder(EntityFactory.createCollectableBuilder(24, 10, 100));
        levelBuilder.addEntityBuilder(EntityFactory.createCollectableBuilder(27, 11, 100));
        levelBuilder.addEntityBuilder(EntityFactory.createCollectableBuilder(30, 12, 100));
        levelBuilder.addEntityBuilder(EntityFactory.createCollectableBuilder(33, 13, 100));

        //add entities to the level
        EntityBuilder player = EntityFactory.createPlayerBuilder(16f, 9.5f);
        levelBuilder.addEntityBuilder(player);

        levelBuilder.addEntityBuilder(EntityFactory.createCollectableBuilder(41.5f, 15, 500));

        level = new Level(this, levelBuilder, name);
        level.getCamera().setFollowEntity(level.getEntity(player.id));
    }

    private void generateMap(LevelBuilder levelBuilder){
        TileSet tileSet = new TileSet("texture1", 8, 8);
        levelBuilder.setMap(new Map(tileSet, 64, 32));
        MapUtils.generateBasicMap(levelBuilder.getMap());
    }

    @Override
    public void onPress(MotionEvent event, int index) {
        for(InputListener inputListener: inputListeners){
            inputListener.onPress(event, index);
        }
    }

    @Override
    public void onMove(MotionEvent event, int index) {
        for(InputListener inputListener: inputListeners){
            inputListener.onMove(event, index);
        }
    }

    @Override
    public void onRelease(MotionEvent event, int index) {
        for(InputListener inputListener: inputListeners){
            inputListener.onRelease(event, index);
        }
    }

    public void addInputListener(InputListener listener){
        inputListeners.add(listener);
    }

    /**
     * @return screen halfWidth
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * @return screen halfHeight
     */
    public int getScreenHeight() {
        return screenHeight;
    }


}
