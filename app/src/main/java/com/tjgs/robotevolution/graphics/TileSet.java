package com.tjgs.robotevolution.graphics;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.manager.TextureManager;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 */
public class TileSet {

    @Expose
    public String textureName;

    @Expose
    public int widthTiles;

    @Expose
    public int heightTiles;

    public TileSet(String textureName, int widthTiles, int heightTiles){
        this.textureName = textureName;
        this.widthTiles = widthTiles;
        this.heightTiles = heightTiles;
    }

    public Texture getTexture(){
        return TextureManager.getTexture(textureName);
    }

    public TextureAtlas getAtlas(){
        return TextureManager.getTextureAtlas(widthTiles, heightTiles);
    }

    public void setTileSetSize(int widthTiles, int heightTiles){
        this.widthTiles = widthTiles;
        this.heightTiles = heightTiles;
    }

}
