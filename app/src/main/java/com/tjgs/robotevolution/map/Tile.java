package com.tjgs.robotevolution.map;

import com.google.gson.annotations.Expose;

public class Tile {

	//id of texture for rendering
	@Expose private int textureID;

    //whether the tile is opaque or has alpha
	private boolean opaque;

    /**
     * Creates a new Tile with given texture id
     * @param textureID texture id
     */
	public Tile(int textureID){
		this.textureID = textureID;
		opaque  = false;
	}

    /**
     * @return texture id of tile
     */
	public int getTextureID() {
		return textureID;
	}

    /**
     * @param textureID new tile texture id
     */
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

    /**
     * @return whether tile is opaque
     */
	public boolean isOpaque() {
		return opaque;
	}

}
