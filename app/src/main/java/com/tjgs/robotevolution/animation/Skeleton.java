package com.tjgs.robotevolution.animation;

import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.Texture;
import com.tjgs.robotevolution.graphics.TextureAtlas;

import java.util.HashMap;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class Skeleton {

    protected HashMap<String, Integer> boneTextureMap;

    protected Bone rootBone;

    protected int currentFrame;

    public Skeleton(Bone rootBone, HashMap<String, Integer> boneTextureMap){
        this.rootBone = rootBone;
        this.boneTextureMap = boneTextureMap;

        this.currentFrame = 0;
    }

    public void setAsKeyFrame(BoneAnimation animation, int frame){

    }

    public void animate(BoneAnimation animation, float dt){

        //TODO: tween bone values between keyframes
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

    public void drawBone(Bone bone, float x, float y, float rot, SpriteBatch batch, TextureAtlas atlas){
        //draw this bone
        batch.addSprite(bone.getX() + x, bone.getY() + y, bone.getRot() + rot,
                bone.getWidth(), bone.getHeight(), bone.getOriginX(), bone.getOriginY(),
                boneTextureMap.get(bone.name), atlas);

        //draw all bones children
        for(Bone b: bone.getChildren()){
            drawBone(b, x, y, bone.getRot() + rot, batch, atlas);
        }
    }

}
