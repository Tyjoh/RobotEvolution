package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.components.model.GraphicsComponentModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.Texture;
import com.tjgs.robotevolution.graphics.TextureAtlas;
import com.tjgs.robotevolution.graphics.TileSet;

/**
 * Created by Tyler Johnson on 5/2/2016.
 *
 */
public class GraphicsComponent implements Component, Comparable<GraphicsComponent>{

    protected PositionComponent positionComp;

    protected float width;
    protected float height;

    protected float originX;
    protected float originY;

    protected int tileId;

    protected TileSet tileSet;
    protected TextureAtlas atlas;
    protected Texture texture;

    public GraphicsComponent(GraphicsComponentModel model){

        this.width = model.width;
        this.height = model.height;

        this.originX = model.originX;
        this.originY = model.originY;

        this.tileSet = model.tileSet;
        this.texture = model.tileSet.getTexture();
        this.atlas = model.tileSet.getAtlas();

        this.tileId = model.tileId;

    }

    public final PositionComponent getPositionComp(){
        return positionComp;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public int getTileId() {
        return tileId;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Texture getTexture() {
        return texture;
    }

    public TileSet getTileSet(){ return tileSet; }

    @Override
    public void onUpdate(float dt) { }

    @Override
    public void onRender(SpriteBatch batch) {
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.GRAPHICS;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION){
            positionComp = (PositionComponent) component;
            Log.d("Graphics", "position comp linked");
        }
    }

    @Override
    public GraphicsComponentModel getCompoenentModel() {
        GraphicsComponentModel model = new GraphicsComponentModel();
        model.width = width;
        model.height = height;
        model.originX = originX;
        model.originY = originY;
        model.tileSet = tileSet;
        model.tileId = tileId;
        return model;
    }

    @Override
    public int compareTo(GraphicsComponent another) {
        return tileSet.textureName.compareTo(another.tileSet.textureName);
    }
}
