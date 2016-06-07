package com.tjgs.robotevolution.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import java.nio.ByteBuffer;

/**
 * Created by Tyler Johnson on 4/27/2016.
 *
 */
public class Texture implements ITexture{

    //texture dimensions
    protected int textureWidth;
    protected int textureHeight;

    //openGL handle for the texture
    private int textureHandle;

    /**
     * Creates new and uploads new texture to the gpu
     * @param context context to load the resource from
     * @param resourceId resource id of the texture to be created
     */
    public Texture(Context context, int resourceId){

        if(resourceId == 0) throw new RuntimeException("Invalid Resource id given");

        int[] handles = new int[1];
        //generate openGL texture
        GLES20.glGenTextures(1, handles, 0);

        //if it was created successfully
        if(handles[0] != 0) {
            //configure loading options
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            textureWidth = bitmap.getWidth();
            textureHeight = bitmap.getHeight();

            //configure texture parameters
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handles[0]);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            glTexImage2D(bitmap);

            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
            bitmap.recycle();
        }

        if(handles[0] == 0)
            throw new RuntimeException("Texture not created");
        //save texture handle
        textureHandle = handles[0];

    }

    /**
     * alternate implementation of GLUtils.texImage2D() source provided by
     * https://groups.google.com/forum/?fromgroups=#!topic/android-developers/uqbDPmP4L8o
     * @param bitmap
     */
    private void glTexImage2D(Bitmap bitmap) {
        // Don't loading using GLUtils, load using gl-method directly
        // GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        int[] pixels = extractPixels(bitmap);
        byte[] pixelComponents = new byte[pixels.length*4];
        int byteIndex = 0;
        for (int i = 0; i < pixels.length; i++) {
            int p = pixels[i];
            // Convert to byte representation RGBA required by gl.glTexImage2D.
            // We don't use intbuffer, because then we
            // would be relying on the intbuffer wrapping to write the ints in
            // big-endian format, which means it would work for the wrong
            // reasons, and it might brake on some hardware.
            pixelComponents[byteIndex++] = (byte) ((p >> 16) & 0xFF); // red
            pixelComponents[byteIndex++] = (byte) ((p >> 8) & 0xFF); //green
            pixelComponents[byteIndex++] = (byte) ((p) & 0xFF); // blue
            pixelComponents[byteIndex++] = (byte) (p >> 24);  // alpha
        }

        ByteBuffer pixelBuffer = ByteBuffer.wrap(pixelComponents);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,
                bitmap.getWidth(), bitmap.getHeight(), 0, GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE, pixelBuffer);
    }

    public static int[] extractPixels(Bitmap src) {
        int x = 0;
        int y = 0;
        int w = src.getWidth();
        int h = src.getHeight();
        int[] colors = new int[w * h];
        src.getPixels(colors, 0, w, x, y, w, h);
        return colors;
    }

    /**
     * @return texture width
     */
    public int getTextureWidth() {
        return textureWidth;
    }

    /**
     * @return texture height
     */
    public int getTextureHeight() {
        return textureHeight;
    }

    /**
     * bind the texture
     */
    public void bind(){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
    }

    /**
     * unbind the texture
     */
    public void unBind(){
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
    }

}
