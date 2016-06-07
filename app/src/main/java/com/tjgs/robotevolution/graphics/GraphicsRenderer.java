package com.tjgs.robotevolution.graphics;

import com.tjgs.robotevolution.components.GraphicsComponent;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class GraphicsRenderer {

    public ArrayList<GraphicsComponent> graphics;

    public GraphicsRenderer(){
        graphics = new ArrayList<>();
    }

    public void addGraphics(GraphicsComponent component){
        this.graphics.add(component);
        Collections.sort(graphics);
    }

    public void removeGraphics(GraphicsComponent component){
        graphics.remove(component);
    }

    public void draw(SpriteBatch batch, Camera camera){

        Texture tex = null;

        for(GraphicsComponent g: graphics){
            if(tex != g.getTexture()){
                tex = g.getTexture();
                batch.render();
                tex.bind();
            }
            batch.addSprite(g.getPositionComp().getX(), g.getPositionComp().getY(),
                    g.getPositionComp().getAngle(), g.getScaleX(), g.getScaleY(),
                    g.getTileId(), g.getAtlas());
        }
        batch.render();
    }

}
