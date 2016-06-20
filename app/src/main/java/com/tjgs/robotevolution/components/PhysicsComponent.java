package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.components.model.PhysicsComponentModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;

import org.joml.Vector2f;

/**
 * Created by Tyler Johnson on 5/2/2016.
 *
 */
public class PhysicsComponent implements Component{

    private static final float minVelConst = 0.01f;

    private static final boolean flyMode = !true;

    protected PositionComponent positionComp;

    protected Vector2f prevPos;

    protected float xVel;
    protected float yVel;

    protected int dx;
    protected int dy;

    protected float xAccel;
    protected float yAccel;

    protected float maxSpeed;
    protected float terminalVelocity;

    protected float gravity;

    public PhysicsComponent(PhysicsComponentModel model){

        prevPos = new Vector2f();

        xAccel = model.xAccel;
        yAccel = model.yAccel;

        maxSpeed = model.maxSpeed;
        terminalVelocity = model.terminalVelocity;

        gravity = model.gravity;

        dx = 0;
        dy = 0;
    }

    @Override
    public void onUpdate(float dt) {

        if(!flyMode)
            regularUpdate(dt);
        else
            flyModeUpdate(dt);

        prevPos.set(positionComp.getPosition());

        positionComp.add(xVel * dt, yVel * dt);

    }

    public void regularUpdate(float dt){

        if(dx != 0){//if is moving in a direction
            xVel += dx * xAccel * dt;
        }else{//if not moving in a direction
            if(xVel > minVelConst){//if velocity is to the right
                xVel -= xAccel * dt * 0.5f;//decelerate left
            }else if(xVel < -minVelConst){//if vel is to the left
                xVel += xAccel * dt * 0.5f;//decelerate right
            }else{
                xVel = 0;//not moving at all
            }
        }

        //limit x velocity to maximum speed
        if(xVel > maxSpeed)
            xVel = maxSpeed;
        else if(xVel < -maxSpeed)
            xVel = -maxSpeed;

        //apply gravity
        yVel -= gravity * dt;

        //limit y velocity to terminalVelocity
        if(yVel > terminalVelocity)
            yVel = terminalVelocity;
        else if(yVel < -terminalVelocity)
            yVel = -terminalVelocity;

    }

    public void flyModeUpdate(float dt){
        if(dx != 0){
            xVel += dx * xAccel * dt;
        }else{
            if(xVel > minVelConst){
                xVel -= xAccel * dt * 0.5f;
            }else if(xVel < -minVelConst){
                xVel += xAccel * dt * 0.5f;
            }else{
                xVel = 0;
            }
        }

        if(xVel > maxSpeed)
            xVel = maxSpeed;
        else if(xVel < -maxSpeed)
            xVel = -maxSpeed;

        if(dy != 0){
            yVel += dy * yAccel * dt;
        }else{
            if(yVel > minVelConst){
                yVel -= yAccel * dt * 0.5f;
            }else if(yVel < -minVelConst){
                yVel += yAccel * dt * 0.5f;
            }else{
                yVel = 0;
            }
        }

        if(yVel > maxSpeed)
            yVel = maxSpeed;
        else if(yVel < -maxSpeed)
            yVel = -maxSpeed;
    }

    public void setDirection(int x, int y){
        this.dx = x;
        this.dy = y;
    }

    public void jump(){
        yVel = 14.5f;
    }

    public float getVelX() {
        return xVel;
    }

    public float getVelY() {
        return yVel;
    }

    public void setVelX(float xVel){
        this.xVel = xVel;
    }

    public void setVelY(float yVel){
        this.yVel = yVel;
    }

    public final Vector2f getPrevPos(){
        return prevPos;
    }

    public float getPrevX() {
        return prevPos.x;
    }

    public float getPrevY() {
        return prevPos.y;
    }

    @Override
    public void onRender(SpriteBatch batch) {  }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.PHYSICS;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION) {
            positionComp = (PositionComponent) component;
            Log.d("Physics", "position comp linked");
        }
    }

    @Override
    public PhysicsComponentModel getCompoenentModel() {
        PhysicsComponentModel model = new PhysicsComponentModel();
        model.xAccel = xAccel;
        model.yAccel = yAccel;
        model.maxSpeed = maxSpeed;
        model.terminalVelocity = terminalVelocity;
        model.gravity = gravity;
        return model;
    }
}
