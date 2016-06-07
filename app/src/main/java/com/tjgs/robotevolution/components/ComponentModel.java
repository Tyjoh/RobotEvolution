package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public interface ComponentModel {

    Component build(Level level);
    
    ComponentType getType();

}
