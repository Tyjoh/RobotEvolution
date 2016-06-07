package com.tjgs.robotevolution.graphics;

/**
 * Created by Tyler Johnson on 5/12/2016.
 *
 */
public interface ITexture {

    /**
     * @return texture width
     */
    int getTextureWidth();

    /**
     * @return texture height
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
