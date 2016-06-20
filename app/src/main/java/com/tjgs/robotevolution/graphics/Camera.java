package com.tjgs.robotevolution.graphics;

import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.Game;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.components.PositionComponent;
import com.tjgs.robotevolution.entity.Entity;

import org.joml.Vector2f;

/**
 * Created by Tyler Johnson on 4/24/2016.
 *
 * Object for managing the camera position in the world
 */
public class Camera {

    private static final String TAG = Camera.class.getSimpleName();

    //projected screen halfWidth = VIEW_WIDTH * 2, same for view halfHeight
    private float VIEW_WIDTH = 8f;
    private float VIEW_HEIGHT;//calculated on initialization

    //device screen dimensions
    private int screenWidth;
    private int screenHeight;

    //projection, view, and combined view-projection matrices for rendering
    private float[] projectionMatrix;
    private float[] viewMatrix;
    private float[] combined;

    //camera position
    protected Vector2f position;

    //target camera position
    protected Vector2f targetPos;

    //true if the cameras position is equal to target position
    private boolean targetReached;

    //interpolation value for moving the camera
    private float tweenSpeed;

    //minimum speed the camera can move
    private float minTween;

    private PositionComponent followPosition;

    private int cameraFollowId;

    private boolean shouldFollow;

    /**
     * Creates a new camera positioned at x, y world coordinates
     * @param x initial x position of camera
     * @param y initial y position of camera
     * @param width screen halfWidth
     * @param height screen halfHeight
     */
    public Camera(float x, float y, int width, int height){
        position = new Vector2f(x, y);
        targetPos = new Vector2f();

        initialize();

        updateScreenSize(width, height);
        updateViewMatrix();
    }

    /**
     * Creates a new camera positioned at 0, 0 world coordinates
     * @param width screen halfWidth
     * @param height screen halfHeight
     */
    public Camera(int width, int height){
        this(0, 0, width, height);
    }

    public Camera(CameraModel model){

        position = model.position;

        this.targetPos = model.targetPos;

        this.tweenSpeed = model.tweenSpeed;

        this.cameraFollowId = model.cameraFollowId;

        initialize();

        DisplayMetrics metrics = Game.context.getResources().getDisplayMetrics();

        updateScreenSize(metrics.widthPixels, metrics.heightPixels);
        updateViewMatrix();
    }

    public void initialize(){
        targetReached = false;

        tweenSpeed = 7f;
        minTween = 0.01f;

        shouldFollow = false;

        projectionMatrix = new float[16];
        viewMatrix = new float[16];
        combined = new float[16];
    }

    /**
     * Sets the cameras target position to move to
     * @param x x position to follow
     * @param y y position to follow
     */
    public void setTarget(float x, float y){
        this.targetPos.set(x, y);
        targetReached = false;

        if(Math.abs(targetPos.x - position.x) < minTween && Math.abs(targetPos.y - position.y) < minTween){
//            this.x = targetX;
//            this.y = targetY;
            position.set(targetPos);
            targetReached = true;
        }
    }

    public void setFollowPosition(PositionComponent posComp, int entityId){
        this.followPosition = posComp;
        this.cameraFollowId = entityId;
        this.setFollow(true);
    }

    public boolean setFollowEntity(Entity entity){
        if(entity.hasComponent(ComponentType.POSITION)) {
            this.setFollowPosition((PositionComponent) entity.getComponent(ComponentType.POSITION), entity.getId());
            return true;
        }
        return false;
    }

    public void setFollow(boolean b){
        this.shouldFollow = b && followPosition != null;
    }

    /**
     * Updates the camera, moves position based on target values
     * @param dt time to step forward
     */
    public void update(float dt){

        //TODO: implement different tween functions for different effects
        if(shouldFollow)
            setTarget(followPosition.getX(), followPosition.getY());

        if(!targetReached) {

            Vector2f disp = new Vector2f();
            targetPos.sub(position, disp).mul(tweenSpeed);

            if (Math.abs(disp.x) > minTween) {
                position.x += disp.x * dt;
            }else{
                position.x = targetPos.x;
            }

            if (Math.abs(disp.y) > minTween) {
                position.y += disp.y * dt;
            }else{
                position.y = targetPos.y;
            }

            targetReached = (position.x == targetPos.x && position.y == targetPos.y);

            updateViewMatrix();
        }

    }

    /**
     * updates the projection matrix with the new screen size and recalculates
     * the combined view-projection matrix
     * @param width screen halfWidth
     * @param height screen halfHeight
     */
    public void updateScreenSize(int width, int height){

        this.screenWidth = width;
        this.screenHeight = height;

        float ratio = (float) width / height;
        float vert = VIEW_WIDTH / ratio;//h = w / ratio
        float horiz = ratio * vert;//w = ratio * h
        VIEW_HEIGHT = vert;

        //right is (-ve) because otherwise coordinates are flipped in horizontal
        Matrix.frustumM(projectionMatrix, 0, horiz, -horiz, -vert, vert, 3, 7);
        //update combined matrix
        Matrix.multiplyMM(combined, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    /**
     * @return combined view and projection matrix for rendering
     */
    public float[] getCombinedMatrix(){
        return combined;
    }

    /**
     * Updates the view matrix and recalculates the combined matrix
     */
    private void updateViewMatrix(){
        //set view matrix
        Matrix.setLookAtM(viewMatrix, 0, position.x, position.y, -3f, position.x, position.y, 0f, 0f, 1.0f, 0f);
        //update combined matrix
        Matrix.multiplyMM(combined, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    /**
     * converts screen coordinates into world view coordinates
     * @param x - x screen coordinate
     * @param y - y screen coordinate
     * @return array with order {x, y, z}
     */
    public float[] screenToWorldCoordinates(float x, float y){

        float[] inverse = new float[16];
        Matrix.invertM(inverse, 0, combined, 0);

        float inx = (2.0f * (x / screenWidth)) - 1.0f;
        float iny = (2.0f * (y / screenHeight)) - 1.0f;

        Log.d(TAG, "normailized coords: " + inx + ", " + iny);

        float[] coords = new float[]{inx, iny, 0, 1};

        Matrix.multiplyMV(coords, 0, inverse, 0, coords, 0);

        coords[3] = 1.0f / coords[3];//w = 1/w
        coords[0] *= coords[3];
        coords[1] *= coords[3];
        coords[2] *= coords[3];

        return new float[]{coords[0], coords[1], coords[2]};
    }

    /**
     * converts world coordinates to screen coordinates
     * @param x x world coordinate
     * @param y y world coordinate
     * @param z z world coordinate
     * @return array with order {x, y}
     */
    public float[] worldToScreenCoordinates(float x, float y, float z){
        //TODO: STUB, needs to be implemented
        return null;
    }

    public int getCameraFollowId(){
        return cameraFollowId;
    }

    public final Vector2f getPosition(){
        return position;
    }

    /**
     * @return camera x position
     */
    public float getX() {
        return position.x;
    }

    /**
     * @return camera y position
     */
    public float getY() {
        return position.y;
    }

    public CameraModel getCameraModel(){
        CameraModel model = new CameraModel();
        model.position = position;
        model.targetPos = targetPos;
        model.tweenSpeed = tweenSpeed;
        model.cameraFollowId = cameraFollowId;
        return model;
    }

}
