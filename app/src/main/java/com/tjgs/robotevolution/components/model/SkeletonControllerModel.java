package com.tjgs.robotevolution.components.model;

import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.components.SkeletonControllerComponent;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/19/2016.
 *
 */
public class SkeletonControllerModel implements ComponentModel{

    @Override
    public Component build(Level level) {
        return new SkeletonControllerComponent(this);
    }

    @Override
    public ComponentType getType() {
        return null;
    }
}
