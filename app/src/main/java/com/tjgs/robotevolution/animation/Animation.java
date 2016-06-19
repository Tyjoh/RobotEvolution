package com.tjgs.robotevolution.animation;

import java.util.HashMap;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class Animation {

    private HashMap<String, BoneAnimation> boneAnimationMap;
    private int[] frameTimes;//TODO: decide whether to put in BoneAnimation class or here

    private int numFrames;

    public Animation(int numFrames){
        boneAnimationMap = new HashMap<>();
        frameTimes = new int[numFrames];
        this.numFrames = numFrames;
    }

    public Animation setFrameTime(int frame, int time){
        if(time >= 0)
            frameTimes[frame] = time;
        return this;
    }

    public void addBoneAnimation(String boneKey, BoneAnimation animation){
        boneAnimationMap.put(boneKey, animation);
    }

    public BoneAnimation getBoneAnimation(String boneKey){
        return boneAnimationMap.get(boneKey);
    }

    public int getFrameLength(int frame){
        return frameTimes[frame];
    }

    public int getNumFrames() {
        return numFrames;
    }
}
