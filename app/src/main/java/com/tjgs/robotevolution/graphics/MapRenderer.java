package com.tjgs.robotevolution.graphics;

import com.tjgs.robotevolution.map.Map;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class MapRenderer {

    protected Map map;

    public MapRenderer(Map map){
        this.map = map;
    }

    /**
     * Adds all visible tiles to the sprite batch
     * @param batch batch to draw to
     * @param camera camera for culling non visible tiles
     */
    public void draw(SpriteBatch batch, Camera camera){

        map.getTileSet().getTexture().bind();

        int startX = (int)camera.getX() - 9;
        int endX = (int)camera.getX() + 9;

        if(startX < 0) startX = 0;
        if(endX > map.getWidth()) endX = map.getWidth();

        int startY = (int)camera.getY() - 5;
        int endY = (int)camera.getY() + 6;

        if(startY < 0) startY = 0;
        if(endY > map.getHeight()) endY = map.getHeight();

        for(int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {

                int texId = map.getTileLayer1(x, y).getTextureID();
                //check for valid texture
//                if(texId != -1)
//                    batch.addSprite(x + 0.5f, y + 0.5f, 0, texId);

                if(texId != -1)
                    batch.addSprite(x + 0.5f, y + 0.5f, 0, 1, 1, texId, map.getTileSet().getAtlas());
            }
        }

        batch.render();
    }

}
