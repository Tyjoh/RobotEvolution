package com.tjgs.robotevolution.animation;

import android.util.Log;

import com.tjgs.robotevolution.components.PositionComponent;
import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.TextureAtlas;

import java.util.HashMap;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class Skeleton {

    public static final String TAG = Skeleton.class.getSimpleName();

    //TODO: remove bone texture map and store directly in bone object
    protected HashMap<String, Integer> boneTextureMap;

    protected HashMap<String, Bone> boneMap;

    protected Bone rootBone;

    private int changeAnimationTime = 200;
    private boolean isChanged;

    protected Animation lastAnimation;
    protected Animation currentAnimation;
    protected int currentFrame;
    protected int timeAccumulator;

    public Skeleton(Bone rootBone, HashMap<String, Integer> boneTextureMap){
        this.rootBone = rootBone;
        this.boneTextureMap = boneTextureMap;

        this.currentFrame = 0;

        boneMap = new HashMap<>();
        buildBoneMap(rootBone);
    }

    private void buildBoneMap(Bone bone){
        boneMap.put(bone.name, bone);
        for(Bone b: bone.getChildren()){
            buildBoneMap(b);
        }
    }

    public void setAnimation(Animation animation){
        this.currentAnimation = animation;
    }

    public void setAsKeyFrame(int frame){
        currentFrame = frame;
        animateBone(rootBone, 0, 0);
        update();
    }

    public void animate(float dt){
        int time = (int)(dt * 1000);

        int frameTime = currentAnimation.getFrameLength(currentFrame);

        animateBone(rootBone, timeAccumulator, frameTime);

        update();

        timeAccumulator += time;
        if(timeAccumulator >= frameTime){
            timeAccumulator = timeAccumulator - frameTime;
            currentFrame++;
        }

        if(currentFrame >= currentAnimation.getNumFrames()){
            currentFrame = 0;
        }

    }

    private void animateBone(Bone bone, int elapsed, int frameTime){
        BoneAnimation animation = currentAnimation.getBoneAnimation(bone.name);

        if(animation != null) {
            int nextFrame = currentFrame + 1;

            if (nextFrame >= currentAnimation.getNumFrames()) {
                nextFrame = 0;
            }

            float startRot = animation.getRotationVal(currentFrame);
            float endRot = animation.getRotationVal(nextFrame);

            float diff = endRot - startRot;

            int dir = animation.getDirection(currentFrame);

            float speed;

            if(Math.signum(diff) == Math.signum(dir)){
                speed = diff / (float) frameTime;
            }else{
                speed = dir * (((float)Math.PI * 2) - Math.abs(diff)) / (float) frameTime;
            }

            bone.setRotation(startRot + (speed * elapsed));

            float startLen = animation.getLengthVal(currentFrame);
            float endLen = animation.getLengthVal(nextFrame);

            diff = endLen - startLen;

            speed = diff / (float) frameTime;

            bone.setLength(bone.getBoneLength() + startLen + (speed * elapsed));
        }

        //update children
        for (Bone b: bone.getChildren()){
            animateBone(b, elapsed, frameTime);
        }

    }

    public final Bone getBone(String key){
        return boneMap.get(key);
    }

    public void update(){
        rootBone.recalculate(0f);
    }

    public Bone getRootBone(){
        return rootBone;
    }

    public void draw(float x, float y, SpriteBatch batch, TextureAtlas atlas){
        this.drawBone(rootBone, x, y, 0f, batch, atlas);
    }

    protected void drawBone(Bone bone, float x, float y, float rot, SpriteBatch batch, TextureAtlas atlas){
        //draw this bone
        bone.drawBone(bone, x, y, rot, batch, atlas, boneTextureMap.get(bone.name));

        //draw all bones children
        for(Bone b: bone.getChildren()){
            drawBone(b, x, y, bone.getRot() + rot, batch, atlas);
        }
    }

}
