package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.collision.EntityCollisionListener;
import com.tjgs.robotevolution.components.model.ColliderComponentModel;
import com.tjgs.robotevolution.collision.MapCollisionListener;

import java.util.ArrayList;

/**
 * Created by Tyler Johnson on 5/3/2016.
 *
 */

public class ColliderComponent implements Component{

    protected float width;

    protected float height;

    protected PositionComponent positionComp;
    protected PhysicsComponent physicsComp;

    protected ArrayList<MapCollisionListener> mapCollisionListeners;
    protected ArrayList<EntityCollisionListener> entityCollisionListeners;

    public ColliderComponent(ColliderComponentModel model){

        this.width = model.width;
        this.height = model.height;

        mapCollisionListeners = new ArrayList<>();
        entityCollisionListeners = new ArrayList<>();
    }

    public void resolveLeftCollision(float penetration){
        positionComp.addX(penetration);
        for(MapCollisionListener listener: mapCollisionListeners){
            listener.onLeftCollision(penetration);
        }
    }

    public void resolveRightCollision(float penetration){
        positionComp.addX(-penetration);
        for(MapCollisionListener listener: mapCollisionListeners){
            listener.onRightCollision(penetration);
        }
    }

    public void resolveTopCollision(float penetration){
        positionComp.addY(-penetration);
        for(MapCollisionListener listener: mapCollisionListeners){
            listener.onTopCollision(penetration);
        }
    }

    public void resolveBottomCollision(float penetration){
        positionComp.addY(penetration);
        for(MapCollisionListener listener: mapCollisionListeners){
            listener.onBottomCollision(penetration);
        }
    }

    public void collide(int id){
        for(EntityCollisionListener listener: entityCollisionListeners){
            listener.collide(id);
        }
    }

    public void addMapColliderListener(MapCollisionListener listener){
        mapCollisionListeners.add(listener);
    }

    public void addEntityCollisionListener(EntityCollisionListener listener){
        entityCollisionListeners.add(listener);
    }

    public float getLeftBound(){ return positionComp.getX() - (width/2f); }

    public float getRightBound(){
        return positionComp.getX() + (width/2f);
    }

    public float getLowerBound(){
        return positionComp.getY() - (height/2f);
    }

    public float getUpperBound(){
        return positionComp.getY() + (height/2f);
    }

    public float getPrevLeftBound(){
        return physicsComp.getPrevX() - (width/2f);
    }

    public float getPrevRightBound(){
        return physicsComp.getPrevX() + (width/2f);
    }

    public float getPrevLowerBound(){
        return physicsComp.getPrevY() - (height/2f);
    }

    public float getPrevUpperBound(){
        return physicsComp.getPrevY() + (height/2f);
    }

    public PhysicsComponent getPhysics(){
        return physicsComp;
    }

    @Override
    public void onUpdate(float dt) { }

    @Override
    public void onRender() { }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.MAP_COLLIDER;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION) {
            positionComp = (PositionComponent) component;
            Log.d("CollisionComponent", "position comp linked");
        }

        if(component.getComponentType() == ComponentType.PHYSICS) {
            physicsComp = (PhysicsComponent) component;
            Log.d("CollisionComponent", "physics comp linked");
        }
    }

    @Override
    public ColliderComponentModel getCompoenentModel() {
        ColliderComponentModel model = new ColliderComponentModel();
        model.width = width;
        model.height = height;
        return model;
    }
}
