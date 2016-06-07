package com.tjgs.robotevolution.graphics;

import com.google.gson.annotations.Expose;

/**
 * Created by Tyler Johnson on 5/28/2016.
 *
 */
public class CameraModel {

    @Expose
	public float x;
    @Expose
    public float y;

    //target camera position
    @Expose
    public float targetX;
    @Expose
    public float targetY;

    //interpolation value for moving the camera
    @Expose
    public float tweenSpeed = 7f;

    @Expose
    public int cameraFollowId;
    
    public float zoom = 1f;


    /**
     * Sets the cameras target position to move to
     * @param x x position to follow
     * @param y y position to follow
     */
    public void setTarget(float x, float y){
        this.targetX = x;
        this.targetY = y;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }


}
