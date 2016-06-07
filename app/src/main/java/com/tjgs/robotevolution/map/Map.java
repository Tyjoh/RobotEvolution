package com.tjgs.robotevolution.map;

import com.google.gson.annotations.Expose;
import com.tjgs.robotevolution.graphics.TileSet;

public class Map {
	
	/**
	 * Contents of the map with row major order
	 */
    @Expose private CollisionTile[][] collisionLayer;
    @Expose private Tile[][] layer1;
    @Expose private Tile[][] layer2;

    //dimensions of the map
	@Expose private int width;
    @Expose private int height;

    @Expose private TileSet tileSet;

	/**
	 * Creates new map with dimensions in chunks
	 * @param width - width of map
	 * @param height - height of map
	 */
	public Map(TileSet tileSet, int width, int height){

        this.tileSet = tileSet;
        this.width = width;
        this.height = height;

        //allocate arrays
        collisionLayer = new CollisionTile[height][width];
        layer1 = new Tile[height][width];
        layer2 = new Tile[height][width];

        //initialize arrays
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                setCollisionTile(x, y, new CollisionTile());
                setTileLayer1(x, y, new Tile(-1));
                setTileLayer2(x, y, new Tile(-1));
            }
        }

	}

    public TileSet getTileSet(){
        return tileSet;
    }

    public static int[] getTileCoordinates(float x, float y){
        x += 0.5f;
        y += 0.5f;

        return new int[]{(int)x, (int)y};
    }

    /**
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @return collision tile at the given tile coordinates
     */
    public CollisionTile getCollisionTile(int x, int y){
        return collisionLayer[y][x];
    }

    /**
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @return Layer1 tile at the given tile coordinates
     */
    public Tile getTileLayer1(int x, int y){
        return layer1[y][x];
    }

    /**
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @return Layer2 tile at the given tile coordinates
     */
    public Tile getTileLayer2(int x, int y){
        return layer2[y][x];
    }

    /**
     * Sets the collision tile at the given tile coordinates
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @param tile tile to set
     */
    public void setCollisionTile(int x, int y, CollisionTile tile){
        collisionLayer[y][x] = tile;
    }

    /**
     * Sets the layer1 tile at the given tile coordinates
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @param tile tile to set
     */
    public void setTileLayer1(int x, int y, Tile tile){
        layer1[y][x] = tile;
    }

    /**
     * Sets the layer2 tile at the given tile coordinates
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @param tile tile to set
     */
    public void setTileLayer2(int x, int y, Tile tile){
        layer2[y][x] = tile;
    }

    /**
     * @return map width
     */
    public int getWidth(){
        return width;
    }

    /**
     */
    public int getHeight(){
        return height;
    }

}
