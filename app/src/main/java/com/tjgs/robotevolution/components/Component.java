package com.tjgs.robotevolution.components;

/**
 * Created by Tyler Johnson on 5/2/2016.
 *
 */
public interface Component {

    void onUpdate(float dt);
    void onRender();
    ComponentType getComponentType();
    //ComponentType[] getDependencies();
    void linkComponentDependency(Component component);
    ComponentModel getCompoenentModel();

}
