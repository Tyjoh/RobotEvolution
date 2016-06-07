package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.ColliderComponent;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class ColliderComponentModel implements ComponentModel{

    @Expose
    public float width;

    @Expose
    public float height;

    @Override
    public Component build(Level level) {
        return new ColliderComponent(this);
    }

	@Override
	public ComponentType getType() {
		return ComponentType.MAP_COLLIDER;
	}
}
