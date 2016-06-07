package com.tjgs.robotevolution.collision;

import com.tjgs.robotevolution.components.ColliderComponent;
import com.tjgs.robotevolution.map.CollisionTile;
import com.tjgs.robotevolution.map.Map;
import com.tjgs.robotevolution.map.TileType;

import java.util.ArrayList;

/**
 * Created by Tyler Johnson on 5/3/2016.
 *
 */
public class MapCollisionSolver {

    private static final String TAG = MapCollisionSolver.class.getSimpleName();

    private static final float penSlop = 0.05f;

    protected ArrayList<ColliderComponent> components;

    protected Map map;

    public MapCollisionSolver(Map map){

        this.map = map;

        components = new ArrayList<>(50);

    }

    /**
     * Steps the collision solver, resolves map collisions for all componentSlots added to the list
     * @param dt time since last step
     */
    public void step(float dt){
        for (ColliderComponent component : components) {
            stepXAxis(component, dt);
            stepYAxis(component, dt);
        }
    }

    /**
     * Checks for collision along the X axis, this method should be called before checking Y axis
     * collision because of the way slopes are implemented
     * @param component - component to test collisions with
     * @param dt - time passed since last step
     */
    private void stepXAxis(ColliderComponent component, float dt){

        //TODO: heavily comment code and type very detailed algorithm

        //get boundaries of the collider with previous Y axis coordinates because we want to
        //guarantee that only X axis collision is tested, and the last Y coordinate is guaranteed to
        //not intersect the map
        float left = component.getLeftBound();
        float right = component.getRightBound();
        float bottom = component.getPrevLowerBound();
        float top = component.getPrevUpperBound();

        //if the object is moving to the left or in (-ve) direction
        if(component.getPhysics().getVelX() < 0){
            //get the x coordinate of the Y axis to test
            int yTileAxis = (int) left;

            //keep track of whether a collision is detected
            boolean leftCollision = false;

            //loop over all the tiles along the height of the object as long as a collision hasn't been detected
            //slop is used to prevent clipping on the floor due to rounding error in floating point arithmetic
            for (int i = (int)(bottom + penSlop); i <= (int)(top - penSlop) && !leftCollision; i++){

                //find the tile to test against
                CollisionTile testTile = map.getCollisionTile(yTileAxis, i);

                //if the tile is a normal solid tile
                //AND if the bottom of the object is lower than the height of the right side of
                //the tile. This is done so that once in a sloped tile the Y axis collision code
                //can resolve the vertical component so the object is on top of the slope
                if(testTile.getType() == TileType.NORMAL &&
                        bottom + penSlop < i + testTile.getRightHeight()){
                    leftCollision = true;//mark collision detected
                }
            }

            //if a collision was detected resolve it
            if(leftCollision){
                //pass in the distance the object penetrates the tile, a (+ve) value
                component.resolveLeftCollision((yTileAxis + 1f) - left);
            }

        //if the object is moving to the right or a (+ve) value
        }else if(component.getPhysics().getVelX() > 0){
            //find the axis to test on
            int yTileAxis = (int) right;

            boolean rightCollision = false;

            //loop over the tiles along the height of the object
            for (int i = (int)(bottom + penSlop); i <= (int)(top - penSlop) && !rightCollision; i++){

                CollisionTile testTile = map.getCollisionTile(yTileAxis, i);

                //check whether the object is allowed to enter the tile or not
                //detailed explanation in first case
                if(testTile.getType() == TileType.NORMAL &&
                        bottom + penSlop < i + testTile.getLeftHeight()){
                    rightCollision = true;
                }
            }

            //resolve the collision
            if(rightCollision){
                //pass penetration value, also a (+ve) value
                component.resolveRightCollision(right - yTileAxis);
            }
        }
    }

    /**
     * Checks and resolves collisions along the Y axis
     * @param component - component to test
     * @param dt - time since last step
     */
    private void stepYAxis(ColliderComponent component, float dt){

        //TODO: heavily comment code and write detailed algorithm

        //get boundaries of the collider with all current position values, X axis is guaranteed to
        //be collision free other than penetrating into a sloped tile, which this algorithm will resolve
        float left = component.getLeftBound();
        float right = component.getRightBound();
        float bottom = component.getLowerBound();
        float top = component.getUpperBound();

        //if the object is moving downward or not at all
        if(component.getPhysics().getVelY() <= 0){

            //find the Y value of the X axis to test Collisions against
            int xTileAxis = (int) bottom;

            //keep track of collision on bottom of object
            boolean bottomCollision = false;

            //keep track of the highest tile along the bottom of the object
            float largestHeight = bottom - 1f;

            //we need to check the initial X axis tiles and the one above that to allow the object
            //to get onto the slope initially
            for(int j = 0; j < 2; j++) {
                //loop over the tiles along the width of the object
                for (int i = (int) (left + penSlop); i <= (int) (right - penSlop); i++) {

                    //tile we ar currently testing
                    CollisionTile testTile = map.getCollisionTile(i, xTileAxis);

                    //gets the height of the tile at the left and right of the object
                    //if the left x coordinate of the object is less than the left x coordinate of
                    //the tile the value is clamped to the x value of the tile same goes for the
                    //right side
                    float leftHeight = testTile.getLineHeight(i, xTileAxis, left);
                    float rightHeight = testTile.getLineHeight(i, xTileAxis, right);

                    //find value that penetrates furthest into slope, this is corresponds to the
                    //highest side of the tile
                    float maxPen = Math.min(bottom - leftHeight, bottom - rightHeight);

                    //Log.d(TAG, "stepYAxis: LeftHeight: " + leftHeight + "  RightHeight: " + rightHeight + "  Bottom: " + bottom);

                    if (testTile.getType() == TileType.NORMAL && maxPen < 0) {
                        bottomCollision = true;

                        //OPTIMIZATION use maxPen to check and adjust largest height, eliminating an branch
                        if (leftHeight > largestHeight) {
                            largestHeight = leftHeight;
                        }

                        if (rightHeight > largestHeight) {
                            largestHeight = rightHeight;
                        }
                    }
                }
                //check axis above last
                xTileAxis++;
            }

            //if a there was a collision
            if(bottomCollision){
                //resolve it with the penetration depth, a (+ve) value
                component.resolveBottomCollision(largestHeight - bottom);
            }

        //if the object is moving upwards
        }else if(component.getPhysics().getVelY() > 0){
            //get the axis of tiles to check
            int xTileAxis = (int) top;

            //TODO: implement slope collisions for upwards movement

            boolean topCollision = false;

            //loop over the tiles along the width of the object
            for (int i = (int)(left + penSlop); i <= (int)(right - penSlop) && !topCollision; i++){
                //test for collision
                if(map.getCollisionTile(i, xTileAxis).getType() == TileType.NORMAL){
                    topCollision = true;
                }
            }

            //resolve the collision with a (+ve) penetration value
            if(topCollision){
                component.resolveTopCollision(top - xTileAxis);
            }
        }
    }

    /**
     * Adds a component to be tested when solving the map collisions
     * @param component - component to add
     */
    public void addColliderComponent(ColliderComponent component){
        components.add(component);
    }

    /**
     * Removes a component from collision solver
     * @param component
     */
    public void removeColliderComponent(ColliderComponent component){
        components.remove(component);
    }

}
