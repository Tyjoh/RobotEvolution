package com.tjgs.robotevolution.graphics;

import android.opengl.GLES20;

import java.nio.ShortBuffer;

/**
 * Created by Tyler Johnson on 4/23/2016.
 */
public class IndexBuffer {

    private int bufferHandle;

    public IndexBuffer(){
        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        bufferHandle = buffers[0];
    }

    public void sendData(ShortBuffer buffer){
        bind();
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * 2, buffer, GLES20.GL_STATIC_DRAW);
        unbind();
    }

    public void bind(){
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferHandle);
    }

    public void unbind(){
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
