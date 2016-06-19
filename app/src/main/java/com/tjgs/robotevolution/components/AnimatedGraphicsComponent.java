package com.tjgs.robotevolution.components;

import android.util.Log;

import com.tjgs.robotevolution.animation.Animation;
import com.tjgs.robotevolution.animation.Skeleton;
import com.tjgs.robotevolution.components.model.AnimatedGraphicsModel;
import com.tjgs.robotevolution.graphics.SpriteBatch;
import com.tjgs.robotevolution.graphics.Texture;
import com.tjgs.robotevolution.graphics.TextureAtlas;
import com.tjgs.robotevolution.graphics.TileSet;
import com.tjgs.robotevolution.manager.TextureManager;

/**
 * Created by Tyler Johnson on 6/17/2016.
 *
 */
public class AnimatedGraphicsComponent implements Component{

    public static final String TAG = AnimatedGraphicsComponent.class.getSimpleName();

    protected PositionComponent positionComp;

    protected float originX;
    protected float originY;

    protected TileSet tileSet;
    protected TextureAtlas atlas;
    protected Texture texture;

    protected Skeleton skeleton;
    protected Animation animation;

    public AnimatedGraphicsComponent(AnimatedGraphicsModel model){

        this.originX = model.originX;
        this.originY = model.originY;

        this.tileSet = model.tileSet;
        Log.d(TAG, "TEXTURE NAME: " + model.tileSet.textureName);
        this.texture = model.tileSet.getTexture();
        this.atlas = model.tileSet.getAtlas();

        this.skeleton = model.skeleton;
        this.animation = model.animation;

        skeleton.setAnimation(animation);
    }

    @Override
    public void onUpdate(float dt) {
        skeleton.animate(dt);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        texture.bind();
        //TextureManager.getTexture("character").bind();
        skeleton.draw(positionComp.getX(), positionComp.getY(), batch, atlas);
        batch.render();
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.ANIMATED_GRAPHICS;
    }

    @Override
    public void linkComponentDependency(Component component) {
        if(component.getComponentType() == ComponentType.POSITION){
            positionComp = (PositionComponent) component;
            Log.d("Graphics", "position comp linked");
        }
    }

    @Override
    public ComponentModel getCompoenentModel() {
        AnimatedGraphicsModel model = new AnimatedGraphicsModel();
        model.originX = originX;
        model.originY = originY;
        model.tileSet = tileSet;
        model.skeleton = skeleton;
        return model;
    }
}
