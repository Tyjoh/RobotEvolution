package com.tjgs.robotevolution.components;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.tjgs.robotevolution.Game;
import com.tjgs.robotevolution.input.InputListener;
import com.tjgs.robotevolution.collision.MapCollisionListener;

/**
 * Created by Tyler Johnson on 5/7/2016.
 *
 */
public class PlayerControllerComponent implements Component, InputListener, MapCollisionListener{

    private static final String TAG = PlayerControllerComponent.class.getSimpleName();

    protected PositionComponent positionComp;
    protected PhysicsComponent physicsComp;
    protected ColliderComponent colliderComp;

    private float leftBound;
    private float rightBound;

    private static final int NUM_POINTERS = 2;

    private float pointX[];
    private float pointY[];
    private boolean touched[];

    protected boolean grounded;

    public PlayerControllerComponent(){
        grounded = false;
        leftBound = Game.context.getResources().getDisplayMetrics().widthPixels / 4f;
        rightBound = Game.context.getResources().getDisplayMetrics().widthPixels / 2f;

        pointX = new float[NUM_POINTERS];
        pointY = new float[NUM_POINTERS];
        touched = new boolean[NUM_POINTERS];

        for(int i = 0; i < NUM_POINTERS; i++)
            touched[i] = false;

    }

    @Override
    public void onPress(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            touched[pointerId] = true;
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);
        }
    }

    @Override
    public void onMove(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);
        }
    }

    @Override
    public void onRelease(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            touched[pointerId] = false;
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);
        }
    }

    @Override
    public void onUpdate(float dt) {

        physicsComp.setDirection(0, 0);

        for(int i = 0; i < 2; i++) {
            //testing input and camera movement
            if (touched[i] && pointY[i] > 1080 / 4) {

                if (pointX[i] < leftBound) {
                    //move left
                    //camera.setTarget(camera.getX() - 2f, camera.getY());
                    physicsComp.setDirection(-1, 0);
                } else if (pointX[i] > leftBound && pointX[i] < rightBound) {
                    //move right
                    //camera.setTarget(camera.getX() + 2f, camera.getY());
                    physicsComp.setDirection(1, 0);
                } else if (pointX[i] > rightBound) {
                    //jump
                    if (grounded) {
                        physicsComp.jump();
                    }
                }
            }
        }

        grounded = false;

    }

    @Override
    public void onBottomCollision(float penetration) {
        //if a collision on the bottom is detected the player is grounded
        grounded = true;
        physicsComp.setVelY(0);
    }

    @Override
    public void onLeftCollision(float penetration) { }

    @Override
    public void onRightCollision(float penetration) { }

    @Override
    public void onTopCollision(float penetration) { }

    @Override
    public void onRender() { }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PLAYER_CONTROLLER;
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
