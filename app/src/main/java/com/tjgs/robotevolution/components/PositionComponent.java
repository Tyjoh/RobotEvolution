package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.components.model.PositionComponentModel;

/**
 * Created by Tyler Johnson on 5/3/2016.
 *
 */
public class PositionComponent implements Component{

    protected float x;

    protected float y;

    protected float angle;

    /**
     * No Args constructor for deserialization
     */
    public PositionComponent(PositionComponentModel model){
        this.x = model.x;
        this.y = model.y;
        this.angle = model.angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addX(float dx){
        this.x += dx;
    }

    public void addY(float dy){
        this.y += dy;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void onUpdate(float dt) { }

    @Override
    public void onRender() { }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.POSITION;
    }

    @Override
    public void linkComponentDependency(Component component) { }

    @Override
    public PositionComponentModel getCompoenentModel() {
        PositionComponentModel model = new PositionComponentModel();
        model.x = x;
        model.y = y;
        model.angle = angle;
        return model;
    }
}
