package com.tjgs.robotevolution.collision;

import android.util.Log;

import com.tjgs.robotevolution.components.ColliderComponent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class EntityCollisionSolver {

    private static final String TAG = EntityCollisionSolver.class.getSimpleName();

    private static final float penSlop = 0.05f;

    protected ArrayList<ColliderComponent> components;

    protected HashMap<ColliderComponent, Integer> componentMap;

    public EntityCollisionSolver(){
        components = new ArrayList<>();
        componentMap = new HashMap<>();
    }

    /**
     * Steps the collision solver, resolves map collisions for all componentSlots added to the list
     * @param dt time since last step
     */
    public void step(float dt){
        for (int i = 0; i < components.size(); i++) {
            ColliderComponent component = components.get(i);
            for(int j = i + 1; j < components.size(); j++){
                collide(component, components.get(j));
            }
        }
    }

    private void collide(ColliderComponent c1, ColliderComponent c2){

        boolean xOverlap = (c1.getRightBound() > c2.getLeftBound() && c1.getRightBound() < c2.getRightBound())
                || (c1.getLeftBound() > c2.getLeftBound() && c1.getLeftBound() < c2.getRightBound());

        if(xOverlap) {
            boolean yOverlap = (c1.getUpperBound() > c2.getLowerBound() && c1.getUpperBound() < c2.getUpperBound())
                    || (c1.getLowerBound() > c2.getLowerBound() && c1.getLowerBound() < c2.getUpperBound());

            if (yOverlap) {
                c1.collide(componentMap.get(c2));
                c2.collide(componentMap.get(c1));
                Log.d(TAG, "collide: c1 == c2 " + (c1 == c2));
            }

        }

    }

    /**
     * Adds a component to be tested when solving the map collisions
     * @param component - component to add
     */
    public void addColliderComponent(ColliderComponent component, int entityId){
        components.add(component);
        componentMap.put(component, entityId);
    }

    /**
     * Removes a component from collision solver
     * @param component component to remove
     */
    public void removeColliderComponent(ColliderComponent component){
        components.remove(component);
        componentMap.remove(component);
    }

}
