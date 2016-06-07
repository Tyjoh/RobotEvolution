package com.tjgs.robotevolution.entity;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

import java.util.ArrayList;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class EntityBuilder {

	@Expose public String name;
	
    @Expose public ArrayList<ComponentModel> componentModels;

    @Expose public int id;

    public EntityBuilder(){
    	name = "Entity";
        componentModels = new ArrayList<>();
    }

    public Entity build(Level level){
        Entity entity = new Entity(id);

        for(ComponentModel model: componentModels){
            entity.addComponent(model.build(level));
        }

        entity.linkComponents();

        return entity;
    }
    
    public boolean has(ComponentType type){
    	for(ComponentModel model : componentModels){
    		if(model.getType() == type) return true;
    	}
    	return false;
    }
    
    public ComponentModel getComponent(ComponentType type){
    	for(ComponentModel model: componentModels){
    		if(model.getType() == type) return model;
    	}
    	return null;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getKeyName(){
    	return name + " id: " + id;
    }

    public void addComponent(ComponentModel builder){
        this.componentModels.add(builder);
    }

}
