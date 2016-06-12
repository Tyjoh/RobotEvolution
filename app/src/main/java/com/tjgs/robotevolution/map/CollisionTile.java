package com.tjgs.robotevolution.map;


import com.google.gson.annotations.Expose;

public class CollisionTile {

    private static final float clipDist = 0.1f;
    private static final float invClipDist = 1f - clipDist;

	//points for calculating slope of tile
	@Expose private float left;
	@Expose private float right;

	private boolean clipLeftCorner;
    private boolean clipRightCorner;

    //type of collision to use when colliding with this tile
	@Expose private TileType type;

    /**
     *
     */
    public CollisionTile(){
        this(0, 0, TileType.EMPTY);
    }

    /**
     * Creates a new Collision tile with given slope coordinates with normal collision type
     * @param left left point of slope line
     * @param right right point of slope line
     */
	public CollisionTile(float left, float right){
		this(left, right, TileType.NORMAL);
	}

    /**
     * Creates a new Collision tile with given slope coordinates and type
     * @param left left point of slope line
     * @param right right point of slope line
     * @param type type of collision tile
     */
	public CollisionTile(float left, float right, TileType type){
		this.left = left;
		this.right = right;
		this.type = type;
        clipLeftCorner = false;
        clipRightCorner = false;
	}

	public float getLineHeight(int tileX, int tileY, float testX){

		float x1 = tileX;
		float x2 = tileX + 1;
		float y1 = tileY + left;
		float y2 = tileY + right;

        float dx = testX - x1;

        if(clipLeftCorner && dx < clipDist){
            float clipHeight = interpolate(x1, y1, x2, y2, tileX + clipDist);
            return clipHeight - (clipDist - dx);
        }

        if(clipRightCorner && dx > invClipDist){
            float clipHeight = interpolate(x1, y1, x2, y2, tileX + invClipDist);
            return clipHeight - (dx - invClipDist);
        }

        return interpolate(x1, y1, x2, y2, testX);

	}

    protected float interpolate(float x1, float y1, float x2, float y2, float testX){
        float slope = (y2 - y1)/(x2 - x1);//calculate when left/right heights are changed not on in function

        float dx = testX - x1;

        float lineY = (slope * dx) + y1;

        //clamp halfHeight value within tile bounds
        if(lineY > Math.max(y1, y2)){
            lineY = Math.max(y1, y2);
        }else if(lineY < Math.min(y1, y2)){
            lineY = Math.min(y1, y2);
        }

        return lineY;
    }

    public CollisionTile setClipLeftCorner(boolean b){
        clipLeftCorner = b;
        return this;
    }

    public CollisionTile setClipRightCorner(boolean b){
        clipRightCorner = b;
        return this;
    }

    /**
     * @return left slope point
     */
	public float getLeftHeight() {
		return left;
	}

    /**
     * @param left point for the left slope point
     */
	public CollisionTile setLeftHeight(float left) {
		this.left = left;

        return this;
	}

    /**
     * @return right slope point
     */
	public float getRightHeight() {
		return right;
	}

    /**
     * @param right point for the right slope point
     */
	public CollisionTile setRightHeight(float right) {
		this.right = right;
        return this;
	}

    /**
     * @return collision type of tile
     */
	public TileType getType() {
		return type;
	}

    /**
     * @param type collision type to set as
     */
	public CollisionTile setType(TileType type) {
		this.type = type;
        return this;
	}
	
}
