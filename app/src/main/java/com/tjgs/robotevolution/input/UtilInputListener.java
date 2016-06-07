package com.tjgs.robotevolution.input;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.tjgs.robotevolution.Game;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 5/25/2016.
 *
 * Top Left pointer 1 release = save level
 *
 */
public class UtilInputListener implements InputListener{

    private static final String TAG = UtilInputListener.class.getSimpleName();

    private final Game game;

    private Level level;

    private static final int NUM_POINTERS = 3;

    private float pointX[];
    private float pointY[];
    private boolean touched[];

    public UtilInputListener(Game game, Level level){

        this.game = game;
        this.level = level;

        pointX = new float[NUM_POINTERS];
        pointY = new float[NUM_POINTERS];
        touched = new boolean[NUM_POINTERS];

        for(int i = 0; i < NUM_POINTERS; i++)
            touched[i] = false;

    }

    @Override
    public void onPress(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            touched[pointerId] = true;
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);

            //Log.d(TAG, "X: " + pointX[0] + " Y: " + pointY[0]);

            if(pointY[0] < 1080 / 4){
                if(pointX[0] > (1920 / 4) * 3){
                    Log.d(TAG, "On Pointer 1 Release Level will be saved with Name " + level.getName());
                }
            }

        }
    }

    @Override
    public void onMove(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);

            if(pointY[0] < 1080 / 4){
                if(pointX[0] > (1920 / 4) * 3){
                    Log.d(TAG, "On Pointer 1 Release Level will be saved with Name " + level.getName());
                }
            }

        }
    }

    @Override
    public void onRelease(MotionEvent event, int index) {

        int pointerId = MotionEventCompat.getPointerId(event, index);

        if(pointerId < 2) {
            touched[pointerId] = false;
            pointX[pointerId] = MotionEventCompat.getX(event, index);
            pointY[pointerId] = MotionEventCompat.getY(event, index);

            if(pointY[0] < 1080 / 4){
                if(pointX[0] > (1920 / 4) * 3){
                    level.saveLevel();
                }
            }

        }
    }
}
