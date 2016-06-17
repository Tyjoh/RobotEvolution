package com.tjgs.robotevolution.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyjoh on 2016-06-16.
 *
 */
public class Bone {

    protected String name;

    protected Bone parent;
    protected List<Bone> children;

    protected float x;
    protected float y;

    protected float rot;

    public Bone(String name, Bone parent, float x, float y, float rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;

        children = new ArrayList<>();
    }

    public void addChild(Bone bone){
        children.add(bone);
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
}
