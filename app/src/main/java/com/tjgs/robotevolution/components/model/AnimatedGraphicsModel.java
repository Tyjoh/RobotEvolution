package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.Decompose;
import com.tjgs.robotevolution.animation.Skeleton;
import com.tjgs.robotevolution.components.AnimatedGraphicsComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.graphics.TileSet;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/17/2016.
 *
 */
public class AnimatedGraphicsModel implements ComponentModel {

    @Expose
    public float originX = 0f;

    @Expose
    public float originY = 0f;

    @Decompose
    @Expose
    public TileSet tileSet;

    @Expose
    public Skeleton skeleton;

    public AnimatedGraphicsModel(){
        tileSet = new TileSet("", 1, 1);
    }

    @Override
    public AnimatedGraphicsComponent build(Level level) {
        return new AnimatedGraphicsComponent(this);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.ANIMATED_GRAPHICS;
    }
}
