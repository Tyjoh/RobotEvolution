package com.tjgs.robotevolution.animation;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class BoneAnimation {

    private float[] rotationFrames;
    private int[] directions;
    private float[] lengthFrames;

    public BoneAnimation(int numFrames){
        rotationFrames = new float[numFrames];
        directions = new int[numFrames];
        lengthFrames = new float[numFrames];
    }

    public BoneAnimation setFrame(int frame, float rotVal, int dir, float lenVal){
        rotationFrames[frame] = rotVal;
        directions[frame] = dir;
        lengthFrames[frame] = lenVal;
        return this;
    }

    public float getRotationVal(int frame){
        return rotationFrames[frame];
    }

    public int rotationDir(int frame){
        return directions[frame];
    }

    public float getLengthVal(int frame){
        return lengthFrames[frame];
    }

    public int getDirection(int frame){
        return directions[frame];
    }

}
