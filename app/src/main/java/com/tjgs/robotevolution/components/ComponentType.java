package com.tjgs.robotevolution.components;

/**
 * Created by Tyler Johnson on 5/8/2016.
 *
 */
public enum ComponentType {

    POSITION(2, new ComponentType[]{}),
    PHYSICS(3, new ComponentType[]{POSITION}),
    GRAPHICS(5, new ComponentType[]{POSITION}),
    ANIMATED_GRAPHICS(5, new ComponentType[]{POSITION}),
    MAP_COLLIDER(4, new ComponentType[]{POSITION, PHYSICS}),
    PLAYER_CONTROLLER(0, new ComponentType[]{POSITION, PHYSICS, MAP_COLLIDER}),
    AI_CONTROLLER(0, new ComponentType[]{POSITION, PHYSICS, MAP_COLLIDER}),
    COLLISION_HANDLER(6, new ComponentType[]{MAP_COLLIDER});

    private int index;

    private ComponentType[] dependencies;

    ComponentType(int index, ComponentType[] dependencies){
        this.index = index;
        this.dependencies = dependencies;
    }

    public ComponentType[] getDependencies(){
        return dependencies;
    }

    public int getIndex(){
        return index;
    }

}
