package com.tjgs.robotevolution.graphics;

import com.google.gson.annotations.Expose;

import org.joml.Vector2f;

/**
 * Created by Tyler Johnson on 5/28/2016.
 *
 */
public class CameraModel {

    public Vector2f position;

    //target camera position
    public Vector2f targetPos;

    //interpolation value for moving the camera
    @Expose
    public float tweenSpeed = 7f;

    @Expose
    public int cameraFollowId;
    
    public float zoom = 1f;

    public CameraModel(){
        position = new Vector2f();
        targetPos = new Vector2f();
    }

    /**
     * Sets the cameras target position to move to
     * @param x x position to follow
     * @param y y position to follow
     */
    public void setTarget(float x, float y){
        this.targetPos.set(x, y);
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }


}
