package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.components.model.SimpleAIControllerComponentModel;
import com.tjgs.robotevolution.collision.MapCollisionListener;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class SimpleAIControllerComponent implements Component, MapCollisionListener{

    public static final String TAG = SimpleAIControllerComponent.class.getSimpleName();

    protected PositionComponent positionComp;
    protected PhysicsComponent physicsComp;
    protected ColliderComponent colliderComp;

    protected int jumpInterval;

    protected long lastJump;

    public SimpleAIControllerComponent(SimpleAIControllerComponentModel model) {
        jumpInterval = model.jumpInterval;
        lastJump = System.currentTimeMillis();
    }

    @Override
    public void onUpdate(float dt) {

        if(System.currentTimeMillis() - lastJump >= jumpInterval){
            physicsComp.jump();
            lastJump = System.currentTimeMillis();
        }

    }

    @Override
    public void onRender() {

    }

    @Override
    public void onLeftCollision(float penetration) {

    }

    @Override
    public void onRightCollision(float penetration) {

    }

    @Override
    public void onTopCollision(float penetration) {

    }

    @Override
    public void onBottomCollision(float penetration) {

    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.AI_CONTROLLER;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION){
            this.positionComp = (PositionComponent) component;
        }else if(component.getComponentType() == ComponentType.PHYSICS){
            this.physicsComp = (PhysicsComponent) component;
            Log.d(TAG, "Physics component linked");
        }else if(component.getComponentType() == ComponentType.MAP_COLLIDER){
            this.colliderComp = (ColliderComponent) component;
            colliderComp.addMapColliderListener(this);
        }
    }

    @Override
    public ComponentModel getCompoenentModel() {
        return null;
    }
}
