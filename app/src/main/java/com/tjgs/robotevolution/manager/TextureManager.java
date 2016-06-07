package com.tjgs.robotevolution.manager;

import android.content.Context;
import android.util.Log;

import com.tjgs.robotevolution.Game;
import com.tjgs.robotevolution.graphics.Texture;
import com.tjgs.robotevolution.graphics.TextureAtlas;

import java.util.HashMap;

/**
 * Created by Tyler Johnson on 5/23/2016.
 *
 */
public class TextureManager {

    private static String TAG = TextureManager.class.getSimpleName();

    private static HashMap<String, Texture> textures;
    private static HashMap<Integer, TextureAtlas> atlases;
    private static boolean instantiated = false;
    private static Context context = null;

    private TextureManager(){
    }

    public static void instantiate(){
        textures = new HashMap<>();
        atlases = new HashMap<>();
        context = Game.context;
        instantiated = true;
    }

    public static Texture getTexture(String tex){

        if(!instantiated) instantiate();

        Texture texture = textures.get(tex);
        if(texture == null){
            Log.d(TAG, "New texture loaded: " + tex);
            texture = new Texture(context, context.getResources().getIdentifier(tex, "drawable", context.getPackageName()));
            textures.put(tex, texture);
        }//else{
            //Log.d(TAG, "Texture reused: " + tex);
        //}

        return texture;
    }

    public void clearTextures(){
        textures.clear();
    }

    public static TextureAtlas getTextureAtlas(int width, int height){

        if(!instantiated) instantiate();

        int id = (100 * width) + height;

        TextureAtlas atlas = atlases.get(id);
        if(atlas == null){
            Log.d(TAG, "New atlas created: " + width + "x" + height);
            atlas = new TextureAtlas(width, height);
            atlases.put(id, atlas);
        }else{
            //Log.d(TAG, "Atlas reused: " + width + "x" + height);
        }

        return atlas;
    }

}
