package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.components.SimpleAIControllerComponent;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class SimpleAIControllerComponentModel implements ComponentModel{

    @Expose
    public int jumpInterval = 1000;

    public SimpleAIControllerComponentModel(){ }

    @Override
    public SimpleAIControllerComponent build(Level level) {
        return new SimpleAIControllerComponent(this);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.AI_CONTROLLER;
    }
}
