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

    public void collideWithMap(Map map, float dt){
        //all data in should assume that the last frame resolved the system such that
        // no entity intersected the map

        //TODO: collect all potential colliding tiles
        //TODO: iterate over all potential colliding bodies and test/resolve collision

        float left = getLeftBound();
        float right = getRightBound();
        float top = getUpperBound();
        float bottom = getLowerBound();

        if(physicsComp.getVelX() < 0) {

            int x = (int)left;
            for(int y = (int)bottom; y < (int)top; y++) {


            }

        }else{

        }
    }

}
