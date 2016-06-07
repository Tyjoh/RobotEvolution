package com.tjgs.robotevolution.graphics;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * Created by Tyler Johnson on 4/21/2016.
 *
 */
public class VertexBuffer {

    private int bufferHandle;

    public VertexBuffer(){
        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        bufferHandle = buffers[0];
    }

    public void sendData(FloatBuffer buffer){
        bind();
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, buffer.capacity() * 4, buffer, GLES20.GL_STATIC_DRAW);
        unbind();
    }

    public void bind(){
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferHandle);
    }

    public void unbind(){
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

}
