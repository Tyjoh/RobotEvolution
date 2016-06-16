package com.tjgs.robotevolution.components;

import com.tjgs.robotevolution.components.model.CompoundColliderModel;
import com.tjgs.robotevolution.map.CollisionTile;
import com.tjgs.robotevolution.map.Map;
import com.tjgs.robotevolution.map.TileType;

import java.util.ArrayList;

/**
 * Created by Tyler Johnson on 6/12/2016.
 *
 */
public class CompoundColliderCompoent extends ColliderComponent {

    float radius;

    public CompoundColliderCompoent(CompoundColliderModel model) {
        super(model);
        this.radius = model.radius;
    }

    public void testXAxis(Map map, float dt){

        float left = getLeftBound();
        float right = getRightBound();
        float bottom = getPrevLowerBound();
        float top = getPrevUpperBound();

        //if the object is moving to the left or in (-ve) direction
        if (physicsComp.getVelX() < 0) {
            //get the x coordinate of the Y axis to test
            int yTileAxis = (int) left;

            //keep track of whether a collision is detected
            boolean leftCollision = false;

            //loop over all the tiles along the height of the object as long as a collision hasn't been detected
            //slop is used to prevent clipping on the floor due to rounding error in floating point arithmetic
            for (int i = (int) (bottom + penSlop); i <= (int) (top - penSlop) && !leftCollision; i++) {

                //find the tile to test against
                CollisionTile testTile = map.getCollisionTile(yTileAxis, i);


                //if the tile is a normal solid tile
                //AND if the bottom of the object is lower than the halfHeight of the right side of
                //the tile. This is done so that once in a sloped tile the Y axis collision code
                //can resolve the vertical component so the object is on top of the slope
//                if (testTile.getType() == TileType.NORMAL &&
//                        bottom + penSlop < i + testTile.getRightHeight()) {
//
//                    //inspect further
//
//                    leftCollision = true;//mark collision detected
//                }
            }

            //if a collision was detected resolve it
            if (leftCollision) {
                //pass in the distance the object penetrates the tile, a (+ve) value
                resolveLeftCollision((yTileAxis + 1f) - left);
            }

            //if the object is moving to the right or a (+ve) value
        }

    }

    protected void testYAxis(Map map, float dt){
        //TODO: heavily comment code and write detailed algorithm

        //get boundaries of the collider with all current position values, X axis is guaranteed to
        //be collision free other than penetrating into a sloped tile, which this algorithm will resolve
        float left = getLeftBound();
        float right = getRightBound();
        float bottom = getLowerBound();
        float top = getUpperBound();

        //if the object is moving downward or not at all
        if(physicsComp.getVelY() <= 0){

            //find the Y value of the X axis to test Collisions against
            int xTileAxis = (int) bottom;

            //keep track of collision on bottom of object
            boolean bottomCollision = false;

            //keep track of the highest tile along the bottom of the object
            float largestHeight = bottom - 1f;

            //we need to check the initial X axis tiles and the one above that to allow the object
            //to get onto the slope initially
            for(int j = 0; j < 2; j++) {
                //loop over the tiles along the halfWidth of the object
                for (int i = (int) (left + penSlop); i <= (int) (right - penSlop); i++) {

                    //tile we ar currently testing
                    CollisionTile testTile = map.getCollisionTile(i, xTileAxis);

                    //gets the halfHeight of the tile at the left and right of the object
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

                        //OPTIMIZATION use maxPen to check and adjust largest halfHeight, eliminating an branch
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
                resolveBottomCollision(largestHeight - bottom);
            }

            //if the object is moving upwards
        }else if(physicsComp.getVelY() > 0){
            //get the axis of tiles to check
            int xTileAxis = (int) top;

            //TODO: implement slope collisions for upwards movement

            boolean topCollision = false;

            //loop over the tiles along the halfWidth of the object
            for (int i = (int)(left + penSlop); i <= (int)(right - penSlop) && !topCollision; i++){
                //test for collision
                if(map.getCollisionTile(i, xTileAxis).getType() == TileType.NORMAL){
                    topCollision = true;
                }
            }

            //resolve the collision with a (+ve) penetration value
            if(topCollision){
                resolveTopCollision(top - xTileAxis);
            }
        }
    }

}
