package com.tjgs.robotevolution.entity;

import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler Johnson on 5/2/2016.
 *
 */
public class Entity {

    //TODO: optimize by having component enum and an id for each component
    protected List<Component> components;

    protected Component[] componentSlots;

    protected int id;

    /**
     * Create a new entity with a unique id
     * @param id - unique id for the entity
     */
    public Entity(int id){
        this.id = id;
        components = new ArrayList<>();
        componentSlots = new Component[ComponentType.values().length + 5];
    }

    /**
     * @return unique id of the entity
     */
    public int getId(){
        return id;
    }

    /**
     * updates all componentSlots of the entity
     * @param dt - time since last update
     */
    public void update(float dt){
        for(Component comp: components){
            comp.onUpdate(dt);
        }
    }

    /**
     * called after rendering
     */
    public void render(){
        for(Component comp: components){
            comp.onRender();
        }
    }

    public void linkComponents(){
        for(Component component: components){
            for(ComponentType type: component.getComponentType().getDependencies()){
                component.linkComponentDependency(componentSlots[type.getIndex()]);
            }
        }
        //TODO: check for unlinked dependencies and throw exception
    }

    /**
     * Adds a component to this entity, checks for dependencies and links them
     * if a dependency is not found a runtime exception is thrown
     * @param component - component to add
     */
    public void addComponent(Component component) {
        componentSlots[component.getComponentType().getIndex()] = component;

        int insertIndex = 0;
        boolean spotFound = false;

        while(!spotFound && insertIndex < components.size()){
            if(component.getComponentType().getIndex() < components.get(insertIndex).getComponentType().getIndex()) {
                spotFound = true;
            }else {
                insertIndex++;
            }
        }

        components.add(insertIndex, component);
    }

    /**
     * Tests whether this entity has a given component type
     * @param type - type of component
     * @return true if it does, false otherwise
     */
    public boolean hasComponent(ComponentType type){
        return componentSlots[type.getIndex()] != null;
    }

    public Component getComponent(ComponentType type){
        return componentSlots[type.getIndex()];
    }

    public EntityBuilder getEntityBuilder(){
        EntityBuilder builder = new EntityBuilder();
        builder.id = id;

        for(Component c: components){
            ComponentModel model = c.getCompoenentModel();
            if(model != null) {
                builder.addComponent(model);
            }
        }

        return builder;
    }

}
