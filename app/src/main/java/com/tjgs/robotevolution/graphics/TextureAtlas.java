package com.tjgs.robotevolution.graphics;

import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * Created by Tyler Johnson on 4/27/2016.
 *
 */
public class TextureAtlas {

    //number of floats to represent the 4 uv coordinates of a quad
    protected static final int FLOATS_PER_TILE = 8;

    //tile dimensions of the atlas
    @Expose
    protected int width;

    @Expose
    protected int height;

    //array to store precomputed texture coordinates
    @Expose
    protected float textureCoords[];

    /**
     * Creates a new texture atlas with pre generated texture coordinates
     * with certain amount of tiles wide and tall
     * @param widthTiles - number of tiles wide
     * @param heightTiles - number of tiles high
     */
    public TextureAtlas(int widthTiles, int heightTiles){
        this.height = heightTiles;
        this.width = widthTiles;

        generateCoords();
    }

    protected TextureAtlas(){

    }


    /**
     * Generates texture coordinates in order
     * (top left, bottom left, bottom right, top right)
     */
    private void generateCoords(){
        int numFloats = height * width * FLOATS_PER_TILE;
        textureCoords = new float[numFloats];

        //halfWidth and halfHeight of each tile with texture halfWidth normalized to 1
        float wFraction = 1f / width;
        float hFraction = 1f / height;

        //index to use when inserting into the array
        int index = 0;

        //loop over rows
        for(int i = 0; i < height; i++){
            //calculate row dependent y coordinates
            float top = hFraction * i;
            float bottom = top + hFraction;

            //loop over columns
            for(int j = 0; j < width; j++){
                //calculate column dependent x coordinates
                float left = wFraction * j;
                float right = left + wFraction;

                //put coordinates in array (x, y) order and
                //with winding specified in javadoc
                textureCoords[index++] = left;
                textureCoords[index++] = top;

                textureCoords[index++] = left;
                textureCoords[index++] = bottom;

                textureCoords[index++] = right;
                textureCoords[index++] = bottom;

                textureCoords[index++] = right;
                textureCoords[index++] = top;

            }

        }
    }

    /**
     * Inserts texture coordinates of a given texture id into the given array
     * @param array array to insert into
     * @param offset position in array to start inserting
     * @param id the id of the tile to be put in the array
     */
    public void insertTextureCoords(float[] array, int offset, int id){
        //check if data will fit in the array
        if(offset + FLOATS_PER_TILE > array.length)
            throw new RuntimeException("data wont fit in given array");

        //find where the uv coordinates start based on the id
        int texOffset = id * FLOATS_PER_TILE;

        //copy data into given array
        for(int i = 0; i < FLOATS_PER_TILE; i++){
            array[offset++] = textureCoords[texOffset++];
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static void main(String[] args){
        //Testing for the atlas
        TextureAtlas atlas = new TextureAtlas(4, 4);

        float[] testArary = new float[32];

        atlas.insertTextureCoords(testArary, 0, 0);
        atlas.insertTextureCoords(testArary, 8, 15);
        atlas.insertTextureCoords(testArary, 16, 6);
        atlas.insertTextureCoords(testArary, 24, 9);

        System.out.println(Arrays.toString(testArary));


    }

}
