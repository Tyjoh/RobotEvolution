package com.tjgs.robotevolution.map;

public class MapUtils {

    /**
     * Generates a random map with random texture ids
     * @param map map to generate
     * @param numTiles number of tiles in tilemap
     */
	public static void randomMap(Map map, int numTiles){
		//TODO: implement if needed
		
	}

    /**
     * Generates and populates the given map with flat tiles accross the map at given halfHeight
     * @param map map to generate
     * @param h halfHeight of tiles in map
     */
	public static void flatMapFloor(Map map, int h){
        //if halfHeight is not valid
		if(h >= map.getHeight())
			throw new RuntimeException("floor halfHeight is greater than the map halfHeight");

        //generate tiles
		for(int i = 0; i < map.getWidth(); i++){
			for(int j = 0; j < h; j++){
                if(j < h - 1)
				    map.getTileLayer1(i, j).setTextureID(4);
                else {
                    map.getTileLayer1(i, j).setTextureID(0);
                    map.getCollisionTile(i, j).setLeftHeight(1f)
                            .setRightHeight(1f).setType(TileType.NORMAL);
                }
			}
		}
	}

	public static void flatMapRoof(Map map, int roof){

        //if halfHeight is not valid
        if(roof >= map.getHeight() || roof < 0)
            throw new RuntimeException("roof halfHeight is greater than the map halfHeight");

        //generate tiles
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = roof; j < map.getHeight(); j++){
                if(j > roof)
                    map.getTileLayer1(i, j).setTextureID(4);
                else {
                    map.getTileLayer1(i, j).setTextureID(23);
                    map.getCollisionTile(i, j).setLeftHeight(0)
                            .setRightHeight(0).setType(TileType.NORMAL);
                }
            }
        }
	}

    public static void generateWalls(Map map, int width){

        for(int i = 0; i < map.getHeight(); i++){
            for(int j = 0; j < width; j++){
                if(j < width - 1){
                    map.getTileLayer1(j, i).setTextureID(4);
                    map.getTileLayer1(map.getWidth() - j - 1, i).setTextureID(4);
                }else{
                    map.getTileLayer1(j, i).setTextureID(5);
                    map.getCollisionTile(j, i).setLeftHeight(0)
                            .setRightHeight(0).setType(TileType.NORMAL);

                    map.getTileLayer1(map.getWidth() - j - 1, i).setTextureID(3);
                    map.getCollisionTile(map.getWidth() - j - 1, i).setLeftHeight(0)
                            .setRightHeight(0).setType(TileType.NORMAL);
                }
            }
        }
    }

    public static void placeFloatingTile(Map map, int x, int y){
        map.getTileLayer1(x, y).setTextureID(0);
        map.getCollisionTile(x, y).setLeftHeight(1f)
                .setRightHeight(1f).setType(TileType.NORMAL);
    }

    public static void placeRightSlope45(Map map, int x, int y){
        map.getTileLayer1(x, y).setTextureID(56);
        map.getCollisionTile(x, y).setType(TileType.NORMAL).setLeftHeight(0).setRightHeight(1f);
    }

    public static void placeLeftSlope45(Map map, int x, int y){
        map.getTileLayer1(x, y).setTextureID(20);
        map.getCollisionTile(x, y).setType(TileType.NORMAL).setLeftHeight(1).setRightHeight(0);
    }

    public static void placeRightSlope22(Map map, int x, int y){
        map.getTileLayer1(x, y).setTextureID(52);
        map.getCollisionTile(x, y).setType(TileType.NORMAL).setLeftHeight(0).setRightHeight(0.5f);

        map.getTileLayer1(x + 1, y).setTextureID(53);
        map.getCollisionTile(x + 1, y).setType(TileType.NORMAL).setLeftHeight(0.5f).setRightHeight(1);
    }

    public static void placeLeftSlope22(Map map, int x, int y){
        map.getTileLayer1(x, y).setTextureID(24);
        map.getCollisionTile(x, y).setType(TileType.NORMAL).setLeftHeight(1).setRightHeight(0.5f);

        map.getTileLayer1(x + 1, y).setTextureID(25);
        map.getCollisionTile(x + 1, y).setType(TileType.NORMAL).setLeftHeight(0.5f).setRightHeight(0);
    }

    public static void generateBasicMap(Map map){
        MapUtils.flatMapRoof(map, 26);
        MapUtils.generateWalls(map, 8);
        MapUtils.flatMapFloor(map, 8);

        //floating tile collision tests
        MapUtils.placeFloatingTile(map, 18, 10);
        MapUtils.placeFloatingTile(map, 19, 10);
        MapUtils.placeFloatingTile(map, 20, 10);

        MapUtils.placeFloatingTile(map, 10, 15);
        MapUtils.placeFloatingTile(map, 11, 15);
        MapUtils.placeFloatingTile(map, 12, 14);
        MapUtils.placeFloatingTile(map, 13, 13);
        MapUtils.placeFloatingTile(map, 14, 13);

        MapUtils.placeFloatingTile(map, 16, 16);
        MapUtils.placeFloatingTile(map, 17, 16);
        MapUtils.placeFloatingTile(map, 18, 17);
        MapUtils.placeFloatingTile(map, 19, 18);
        MapUtils.placeFloatingTile(map, 20, 18);

        //slope tests
        MapUtils.placeRightSlope22(map, 29, 8);
        MapUtils.placeRightSlope45(map, 31, 9);
        MapUtils.placeFloatingTile(map, 31, 8);

        MapUtils.placeFloatingTile(map, 32, 9);

        MapUtils.placeFloatingTile(map, 32, 8);
        MapUtils.placeFloatingTile(map, 33, 8);
        MapUtils.placeLeftSlope45(map, 33, 9);
        MapUtils.placeLeftSlope22(map, 34, 8);

        MapUtils.placeRightSlope22(map, 38, 8);
        MapUtils.placeRightSlope45(map, 40, 9);
        MapUtils.placeFloatingTile(map, 40, 8);
        MapUtils.placeFloatingTile(map, 41, 9);
        MapUtils.placeFloatingTile(map, 41, 10);
        MapUtils.placeFloatingTile(map, 41, 8);
        MapUtils.placeFloatingTile(map, 42, 8);
        MapUtils.placeLeftSlope45(map, 42, 9);
        MapUtils.placeLeftSlope22(map, 43, 8);
    }
	
}
