package com.tjgs.robotevolution.graphics;

import android.opengl.Matrix;

/**
 * Created by Tyler Johnson on 4/27/2016.
 *
 */
public class SpriteBatch{

    private static final String TAG = SpriteBatch.class.getSimpleName();

    //TODO: pack color in a float instead of 4 values for 1 color
    //floats per vertex = (position size) + (tex coord size) + (color size)
    private static final int FLOATS_PER_VERTEX = 3 + 4 + 2;
    private static final int VERTICES_PER_SPRITE = 4;
    private static final int INDICES_PER_SPRITE = 6;

    //stride in bytes, 4 bytes / float
    public static final int STRIDE = FLOATS_PER_VERTEX * 4;

    //coordinates of the standard 1x1 quad
    private float[] quad = new float[]{
            -0.5f, 0.5f, 0f, 1f,

            -0.5f, -0.5f, 0f, 1f,

            0.5f, -0.5f, 0f, 1f,

            0.5f, 0.5f, 0f, 1f,
    };

    //maximum size of sprites that can fit in a batch before being drawn
    protected int batchSize = 128;

    //arrays to hold vertices for each batch
    private float[] vertices;
    private short[] indices;

    //position of next sprite in the batch
    private int vertexOffset;

    //number of sprites in the batch
    private int numSprites;

    //Texture atlas for tiles
    private TextureAtlas atlas;

    //camera to draw with
    private Camera camera;

    private BatchRenderer renderer;

    private float[] texCoords;
    private float[] transform;
    private float[] resultQuad;

    /**
     * Creates a new Sprite batch that will draw sprites withing the camera view
     * @param camera camera viewing the world
     */
    public SpriteBatch(Camera camera, BatchRenderer renderer){

        this.camera = camera;
        this.renderer = renderer;

        //initialize arrays and positions
        vertices = new float[batchSize * FLOATS_PER_VERTEX * VERTICES_PER_SPRITE];
        indices = new short[batchSize * INDICES_PER_SPRITE];
        vertexOffset = 0;
        numSprites = 0;

        atlas = new TextureAtlas(8, 8);

        short j = 0;

        //initialize indices
        for(int i = 0; i < indices.length; i += 6, j += 4){
            //triangle 1
            indices[i] = j;                 //vertex 0
            indices[i+1] = (short)(j + 1);  //vertex 1
            indices[i+2] = (short)(j + 2);  //vertex 2

            //triangle 2
            indices[i+3] = (short)(j + 2);  //vertex 2
            indices[i+4] = (short)(j + 3);  //vertex 3
            indices[i+5] = j;               //vertex 0
        }

        renderer.loadIndexBuffer(indices);

        texCoords = new float[8];
        transform = new float[16];
        resultQuad = new float[16];

    }

    /**
     * Adds a sprite to the batch at the given coordinate and texture id, texture must
     * be bound before adding sprites to batch
     * @param x sprites x coordinate
     * @param y sprites y coordinate
     * @param rot sprites rotation
     * @param texID texture id of given sprite
     */
    public void addSprite(float x, float y, float rot, int texID){

        float[] texCoords = new float[8];
        atlas.insertTextureCoords(texCoords, 0, texID);

        vertices[vertexOffset++] = quad[0] + x;
        vertices[vertexOffset++] = quad[1] + y;
        vertices[vertexOffset++] = quad[2];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[0];
        vertices[vertexOffset++] = texCoords[1];

        vertices[vertexOffset++] = quad[3] + x;
        vertices[vertexOffset++] = quad[4] + y;
        vertices[vertexOffset++] = quad[5];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[2];
        vertices[vertexOffset++] = texCoords[3];

        vertices[vertexOffset++] = quad[6] + x;
        vertices[vertexOffset++] = quad[7] + y;
        vertices[vertexOffset++] = quad[8];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[4];
        vertices[vertexOffset++] = texCoords[5];

        vertices[vertexOffset++] = quad[9] + x;
        vertices[vertexOffset++] = quad[10] + y;
        vertices[vertexOffset++] = quad[11];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[6];
        vertices[vertexOffset++] = texCoords[7];

        numSprites ++;

        if(numSprites == batchSize){
            render();
        }

    }

    /**
     * Adds a sprite to the batch at the given coordinate and texture id, texture must
     * be bound before adding sprites to batch
     * @param x sprites x coordinate
     * @param y sprites y coordinate
     * @param rot sprites rotation
     * @param scaleX x scale of the sprite
     * @param scaleY y scale of the sprite
     * @param texID texture id of given sprite
     * @param atlas texture atlas to use for texture coordinates
     */
    public void addSprite(float x, float y, float rot, float scaleX, float scaleY, int texID, TextureAtlas atlas){

        atlas.insertTextureCoords(texCoords, 0, texID);

        Matrix.setIdentityM(transform, 0);
        Matrix.translateM(transform, 0, x, y, 0);
        Matrix.scaleM(transform, 0, scaleX, scaleY, 1);
        Matrix.rotateM(transform, 0, rot, 0, 0, 1);//rotate about z axis

        Matrix.multiplyMM(resultQuad, 0, transform, 0, this.quad, 0);

        vertices[vertexOffset++] = resultQuad[0];
        vertices[vertexOffset++] = resultQuad[1];
        vertices[vertexOffset++] = resultQuad[2];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[0];
        vertices[vertexOffset++] = texCoords[1];

        vertices[vertexOffset++] = resultQuad[4];
        vertices[vertexOffset++] = resultQuad[5];
        vertices[vertexOffset++] = resultQuad[6];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[2];
        vertices[vertexOffset++] = texCoords[3];

        vertices[vertexOffset++] = resultQuad[8];
        vertices[vertexOffset++] = resultQuad[9];
        vertices[vertexOffset++] = resultQuad[10];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[4];
        vertices[vertexOffset++] = texCoords[5];

        vertices[vertexOffset++] = resultQuad[12];
        vertices[vertexOffset++] = resultQuad[13];
        vertices[vertexOffset++] = resultQuad[14];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[6];
        vertices[vertexOffset++] = texCoords[7];

        numSprites ++;

        if(numSprites == batchSize){
            render();
        }

    }

//    /**
//     * Adds a sprite to the batch at the given coordinate and texture id, texture must
//     * be bound before adding sprites to batch
//     * @param x sprites x coordinate
//     * @param y sprites y coordinate
//     * @param rot sprites rotation
//     * @param scaleX x scale of the sprite
//     * @param scaleY y scale of the sprite
//     * @param texID texture id of given sprite
//     * @param atlas texture atlas to use for texture coordinates
//     */
//    public void addSprite(float x, float y, float rot, float scaleX, float scaleY, int texID, TextureAtlas atlas){
//
//        float[] texCoords = new float[8];
//
//        atlas.insertTextureCoords(texCoords, 0, texID);
//
//        vertices[vertexOffset++] = (quad[0] * scaleX) + x;
//        vertices[vertexOffset++] = (quad[1] * scaleY) + y;
//        vertices[vertexOffset++] = quad[2];
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = texCoords[0];
//        vertices[vertexOffset++] = texCoords[1];
//
//        vertices[vertexOffset++] = (quad[3] * scaleX) + x;
//        vertices[vertexOffset++] = (quad[4] * scaleY) + y;
//        vertices[vertexOffset++] = quad[5];
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = texCoords[2];
//        vertices[vertexOffset++] = texCoords[3];
//
//        vertices[vertexOffset++] = (quad[6] * scaleX) + x;
//        vertices[vertexOffset++] =(quad[7] * scaleY) + y;
//        vertices[vertexOffset++] = quad[8];
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = texCoords[4];
//        vertices[vertexOffset++] = texCoords[5];
//
//        vertices[vertexOffset++] = (quad[9] * scaleX) + x;
//        vertices[vertexOffset++] = (quad[10] * scaleY) + y;
//        vertices[vertexOffset++] = quad[11];
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = 1f;
//        vertices[vertexOffset++] = texCoords[6];
//        vertices[vertexOffset++] = texCoords[7];
//
//        numSprites ++;
//
//        if(numSprites == batchSize){
//            render();
//        }
//
//    }

    /**
     * Adds a sprite to the batch at the given coordinate and texture id, texture must
     * be bound before adding sprites to batch
     * @param x sprites x coordinate
     * @param y sprites y coordinate
     * @param rot sprites rotation
     * @param texID texture id of given sprite
     * @param atlas texture atlas to use for texture coordinates
     */
    public void addSprite(float x, float y, float rot, int texID, TextureAtlas atlas){

        float[] texCoords = new float[8];

        atlas.insertTextureCoords(texCoords, 0, texID);

        vertices[vertexOffset++] = quad[0] + x;
        vertices[vertexOffset++] = quad[1] + y;
        vertices[vertexOffset++] = quad[2];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[0];
        vertices[vertexOffset++] = texCoords[1];

        vertices[vertexOffset++] = quad[3] + x;
        vertices[vertexOffset++] = quad[4] + y;
        vertices[vertexOffset++] = quad[5];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[2];
        vertices[vertexOffset++] = texCoords[3];

        vertices[vertexOffset++] = quad[6] + x;
        vertices[vertexOffset++] =quad[7] + y;
        vertices[vertexOffset++] = quad[8];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[4];
        vertices[vertexOffset++] = texCoords[5];

        vertices[vertexOffset++] = quad[9] + x;
        vertices[vertexOffset++] = quad[10] + y;
        vertices[vertexOffset++] = quad[11];
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = 1f;
        vertices[vertexOffset++] = texCoords[6];
        vertices[vertexOffset++] = texCoords[7];

        numSprites ++;

        if(numSprites == batchSize){
            render();
        }
    }

    public void render(){
        renderer.loadVertexBuffer(vertices, vertexOffset);
        renderer.render(camera.getCombinedMatrix(), numSprites);

        vertexOffset = 0;
        numSprites = 0;
    }

}
