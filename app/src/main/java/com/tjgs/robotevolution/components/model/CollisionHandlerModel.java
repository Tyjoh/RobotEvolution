package com.tjgs.robotevolution.components.model;

import com.tjgs.robotevolution.components.CollisionHandlerComponent;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class CollisionHandlerModel implements ComponentModel{

    public CollisionHandlerModel() {
    }

    @Override
    public Component build(Level level) {
        return new CollisionHandlerComponent(this, level);
    }

    @Override
    public ComponentType getType() {
        return null;
    }
}
