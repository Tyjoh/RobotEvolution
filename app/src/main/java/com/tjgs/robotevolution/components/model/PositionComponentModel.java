package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.PositionComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

import org.joml.Vector2f;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class PositionComponentModel implements ComponentModel{

    public Vector2f position;

    @Expose
    public float angle;

    public PositionComponentModel(){
        position = new Vector2f();
    }

    @Override
    public Component build(Level level) {
        return new PositionComponent(this);
    }

	@Override
	public ComponentType getType() {
		return ComponentType.POSITION;
	}
}
