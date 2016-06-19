package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.animation.Bone;
import com.tjgs.robotevolution.animation.Skeleton;
import com.tjgs.robotevolution.components.model.SkeletonControllerModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;

/**
 * Created by Tyler Johnson on 6/19/2016.
 *
 */
public class SkeletonControllerComponent implements Component{

    protected PositionComponent posComp;
    protected PhysicsComponent physComp;
    protected Skeleton skeleton;

    public SkeletonControllerComponent(SkeletonControllerModel model){

    }

    @Override
    public void onUpdate(float dt) {
        Bone wheel = skeleton.getBone("Wheel");
        wheel.setRotation(wheel.getRot() - (physComp.xVel / 0.5f)*dt);
    }

    @Override
    public void onRender(SpriteBatch batch) {

    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SKELETON_CONTROLLER;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION){
            posComp = (PositionComponent) component;
        }else if(component.getComponentType() == ComponentType.PHYSICS){
            physComp = (PhysicsComponent) component;
        }else if(component.getComponentType() == ComponentType.ANIMATED_GRAPHICS){
            skeleton = ((AnimatedGraphicsComponent) component).skeleton;
        }
    }

    @Override
    public ComponentModel getCompoenentModel() {
        return new SkeletonControllerModel();
    }
}
