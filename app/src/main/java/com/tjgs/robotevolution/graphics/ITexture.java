package com.tjgs.robotevolution.graphics;

/**
 * Created by Tyler Johnson on 5/12/2016.
 *
 */
public interface ITexture {

    /**
     * @return texture halfWidth
     */
    int getTextureWidth();

    /**
     * @return texture halfHeight
     */
    int getTextureHeight();

    /**
     * bind the texture
     */
    void bind();

    /**
     * unbind the texture
     */
    void unBind();

}
