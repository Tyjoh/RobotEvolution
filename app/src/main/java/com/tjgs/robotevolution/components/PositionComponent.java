package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.components.model.PositionComponentModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;

import org.joml.Vector2f;

/**
 * Created by Tyler Johnson on 5/3/2016.
 *
 */
public class PositionComponent implements Component{

    protected Vector2f position;

    protected float angle;

    /**
     * No Args constructor for deserialization
     */
    public PositionComponent(PositionComponentModel model){
        this.position = model.position;
        this.angle = model.angle;
    }

    public Vector2f getPosition(){
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getAngle() {
        return angle;
    }

    public void setPosition(Vector2f pos){
        this.position.set(pos);
    }

    public void setPosition(float x, float y){
        this.position.set(x, y);
    }

    public void add(float x, float y){
        this.position.add(x, y);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void onUpdate(float dt) { }

    @Override
    public void onRender(SpriteBatch batch) { }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.POSITION;
    }

    @Override
    public void linkComponentDependency(Component component) { }

    @Override
    public PositionComponentModel getCompoenentModel() {
        PositionComponentModel model = new PositionComponentModel();
        model.position = position;
        model.angle = angle;
        return model;
    }
}
