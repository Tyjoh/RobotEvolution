package com.tjgs.robotevolution.animation;

import android.util.Log;

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

    protected float length;

    //graphics definition
    protected float originX;
    protected float originY;

    protected float width;
    protected float height;

    public Bone(String name, Bone parent, float length, float rot, float originX, float originY, float width, float height) {
        this.name = name;

        this.length = length;
        this.rot = rot;

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

    public void addChild(Bone bone){
        children.add(bone);
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

    public float getLength() {
        return length;
    }
}
