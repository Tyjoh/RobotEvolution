package com.tjgs.robotevolution.level;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.entity.EntityBuilder;
import com.tjgs.robotevolution.graphics.CameraModel;
import com.tjgs.robotevolution.map.Map;

import java.util.ArrayList;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class LevelBuilder {

    @Expose
    protected CameraModel camera;

    @Expose
    protected ArrayList<EntityBuilder> entityBuilders;

    @Expose
    protected Map map;

    public LevelBuilder(){
        entityBuilders = new ArrayList<>();
    }

    public void setMap(Map map){
        this.map = map;
    }

    public void addEntityBuilder(EntityBuilder builder){
        entityBuilders.add(builder);
    }

    public void setCamera(CameraModel cam){
        this.camera = cam;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<EntityBuilder> getEntityBuilders() {
        return entityBuilders;
    }

    public CameraModel getCamera() {
        return camera;
    }

	public void removeEntityBuilder(EntityBuilder entity) {
		entityBuilders.remove(entity);
	}
}
