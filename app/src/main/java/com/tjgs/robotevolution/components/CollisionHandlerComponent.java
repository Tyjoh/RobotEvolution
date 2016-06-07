package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.collision.EntityCollisionListener;
import com.tjgs.robotevolution.components.model.CollisionHandlerModel;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class CollisionHandlerComponent implements Component, EntityCollisionListener{

    public static final String TAG = CollisionHandlerComponent.class.getSimpleName();

    private boolean mapColliderLinked;

    private int score;

    private Level level;

    public CollisionHandlerComponent(CollisionHandlerModel model, Level level){
        this.level = level;
        mapColliderLinked = false;
        score++;
    }

    @Override
    public void collide(int entityId) {
        //Log.d(TAG, "collide: " + entityId);
        score += 100;
        level.removeEntity(entityId);
        Log.d(TAG, "Score: " + score);
    }

    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender() {

    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.COLLISION_HANDLER;
    }

    @Override
    public void linkComponentDependency(Component component) {
        Log.d(TAG, "linkComponentDependency");
                
        if(component.getComponentType() == ComponentType.MAP_COLLIDER && !mapColliderLinked){
            ((ColliderComponent)component).addEntityCollisionListener(this);
            mapColliderLinked = true;
        }
    }

    @Override
    public ComponentModel getCompoenentModel() {
        return null;
    }
}
