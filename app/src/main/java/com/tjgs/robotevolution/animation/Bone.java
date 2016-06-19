package com.tjgs.robotevolution.animation;

import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.TextureAtlas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class Bone {

    public static final String TAG = Bone.class.getSimpleName();

    protected String name;

    //bone hierarchy
    protected Bone parent;
    protected List<Bone> children;

    //bone orientation
    protected float x;
    protected float y;
    protected float rot;

    protected float length;//used in simulation
    protected final float boneLength;//constant doesn't change

    //graphics orientation with respect to bone
    protected float originX;
    protected float originY;

    protected float width;
    protected float height;

    protected float drawRotation;

    public Bone(String name, Bone parent, float length, float boneRot, float drawRot, float width, float height, float originX, float originY) {
        this.name = name;

        this.boneLength = length;
        this.length = length;
        this.rot = boneRot;

        this.drawRotation = drawRot;

        this.originX = originX;
        this.originY = originY;

        this.width = width;
        this.height = height;

        x = 0;
        y = 0;

        this.parent = parent;
        if(parent != null)
            parent.addChild(this);

        children = new ArrayList<>();
    }

    public void recalculate(float rot){

        if(parent != null) {
            x = parent.getX() + (float) Math.cos(rot) * parent.getLength();
            y = parent.getY() + (float) Math.sin(rot) * parent.getLength();
        }

        //Log.d(TAG, "BONE: " + x + ", " + y);

        for(Bone b: children) b.recalculate(this.rot + rot);

    }

    public void drawBone(Bone bone, float x, float y, float rot, SpriteBatch batch, TextureAtlas atlas, int texID){
        if(texID > -1)
            batch.addSprite(this.x + x, this.y + y, this.rot + drawRotation + rot,
                    width, height, originX, originY, texID, atlas);
    }

    public void addChild(Bone bone){
        children.add(bone);
    }

    public void setRotation(float rot){
        this.rot = rot;
        if(rot > Math.PI*2){
            this.rot -= Math.PI*2;
        }else if(rot < -Math.PI*2){
            this.rot += Math.PI*2;
        }
    }

    public void setLength(float length){
        this.length = length;
    }

    public final List<Bone> getChildren(){
        return children;
    }

    public Bone getParent() {
        return parent;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRot() {
        return rot;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLength(){return length; }

    public float getBoneLength() {
        return boneLength;
    }
}
