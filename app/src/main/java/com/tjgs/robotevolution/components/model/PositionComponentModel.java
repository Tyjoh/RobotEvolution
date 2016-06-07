package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.PositionComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class PositionComponentModel implements ComponentModel{

    @Expose
    public float x;

    @Expose
    public float y;

    @Expose
    public float angle;

    @Override
    public Component build(Level level) {
        return new PositionComponent(this);
    }

	@Override
	public ComponentType getType() {
		return ComponentType.POSITION;
	}
}
