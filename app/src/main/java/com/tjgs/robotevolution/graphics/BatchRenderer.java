package com.tjgs.robotevolution.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Tyler Johnson on 5/12/2016.
 *
 */
public class BatchRenderer{

    private int STRIDE = SpriteBatch.STRIDE;

    //openGL handles for the vertex attributes
    private int positionAttrib;
    private int textureAttrib;
    private int colorAttrib;

    //handle for the view-projection matrix uniform variable
    private int viewProjMatrix;

    //base shader for drawing
    private Shader shader;

    //buffer object for storing the batch data on the gpu
    private VertexBuffer vbo;
    private IndexBuffer ibo;

    //Buffers for uploading data to the gpu
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private int positionOffset = 0;
    private int colorOffset = 12;
    private int textureOffset = 28;

    public BatchRenderer(){

        shader = new Shader();

        //get openGL shader locations
        positionAttrib = shader.getAttribLocation("vPosition");
        colorAttrib = shader.getAttribLocation("vertColor");
        textureAttrib = shader.getAttribLocation("texCoord");
        viewProjMatrix = shader.getUniformLocation("uVPMatrix");

        //create index buffer because it never changes
        ibo = new IndexBuffer();

        //allocate a place for vertices on the gpu
        vbo = new VertexBuffer();

    }


    public void render(float[] VPMatrix, int numSprites) {
        //loadVertexBuffer();
        vbo.sendData(vertexBuffer);

        //start shader
        shader.useProgram();
        //enable vertex data
        vbo.bind();

        //setup position attributes (handle, attrib size, type, false, stride in bytes, position for interleaving(bytes))
        GLES20.glVertexAttribPointer(positionAttrib, 3, GLES20.GL_FLOAT, false, STRIDE, positionOffset);
        GLES20.glEnableVertexAttribArray(positionAttrib);

        //setup color attribute
        GLES20.glVertexAttribPointer(colorAttrib, 4, GLES20.GL_FLOAT, false, STRIDE, colorOffset);
        GLES20.glEnableVertexAttribArray(colorAttrib);

        //setup texture coordinate attribute
        GLES20.glVertexAttribPointer(textureAttrib, 2, GLES20.GL_FLOAT, false, STRIDE, textureOffset);
        GLES20.glEnableVertexAttribArray(textureAttrib);

        //upload view-projection matrix to uniform
        GLES20.glUniformMatrix4fv(viewProjMatrix, 1, false, VPMatrix, 0);

        //enable index buffer and draw elements
        ibo.bind();
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, numSprites * 6, GLES20.GL_UNSIGNED_SHORT, 0);
        ibo.unbind();

        //disable attributes
        GLES20.glDisableVertexAttribArray(textureAttrib);
        GLES20.glDisableVertexAttribArray(colorAttrib);
        GLES20.glDisableVertexAttribArray(positionAttrib);
        vbo.unbind();
    }

    /**
     * Loads the index buffer object with the data in the indices array
     */
    public void loadIndexBuffer(short[] indices){
        ByteBuffer bb = ByteBuffer.allocateDirect(indices.length * 2);

        bb.order(ByteOrder.nativeOrder());
        indexBuffer = bb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ibo.sendData(indexBuffer);
    }

    public void loadVertexBuffer(float[] vertices, int length) {
        ByteBuffer bb = ByteBuffer.allocateDirect(length * 4);

        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices, 0, length);
        vertexBuffer.position(0);

    }
}
