package com.tjgs.robotevolution.components.model;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.Decompose;
import com.tjgs.robotevolution.components.Component;
import com.tjgs.robotevolution.components.GraphicsComponent;
import com.tjgs.robotevolution.components.ComponentModel;
import com.tjgs.robotevolution.components.ComponentType;
import com.tjgs.robotevolution.graphics.TileSet;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class GraphicsComponentModel implements ComponentModel{

    @Expose
    public float width = 1f;

    @Expose
    public float height = 1f;

    @Decompose
    @Expose
    public TileSet tileSet;

    @Expose
    public int tileId = 1;
    
    public GraphicsComponentModel() {
        tileSet = new TileSet("", 1, 1);
	}

    public void setTileSetSize(int width, int height){
        tileSet.setTileSetSize(width, height);
    }
    
    @Override
    public Component build(Level level) {
        return new GraphicsComponent(this);
    }

	@Override
	public ComponentType getType() {
		return ComponentType.GRAPHICS;
	}
}
