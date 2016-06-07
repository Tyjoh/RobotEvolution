package com.tjgs.robotevolution.collision;

/**
 * Created by Tyler Johnson on 5/7/2016.
 *
 */
public interface MapCollisionListener {

    void onLeftCollision(float penetration);
    void onRightCollision(float penetration);
    void onTopCollision(float penetration);
    void onBottomCollision(float penetration);

}
