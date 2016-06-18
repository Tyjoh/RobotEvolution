package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.graphics.SpriteBatch;

/**
 * Created by Tyler Johnson on 5/2/2016.
 *
 */
public interface Component {

    void onUpdate(float dt);
    void onRender(SpriteBatch batch);
    ComponentType getComponentType();
    void linkComponentDependency(Component component);
    ComponentModel getCompoenentModel();

}
