package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.PhysicsComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class PhysicsComponentModel implements ComponentModel{

    @Expose
    public float xAccel = 20f;

    @Expose
    public float yAccel = 20f;

    @Expose
    public float maxSpeed = 6f;

    @Expose
    public float terminalVelocity= 14f;

    @Expose
    public float gravity = 30f;

    @Override
    public Component build(Level level) {
        return new PhysicsComponent(this);
    }

	@Override
	public ComponentType getType() {
		return ComponentType.PHYSICS;
	}
}
