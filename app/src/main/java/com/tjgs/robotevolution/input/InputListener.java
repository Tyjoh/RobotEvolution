package com.tjgs.robotevolution.input;

import android.view.MotionEvent;

/**
 * Created by Tyler Johnson on 4/25/2016.
 *
 */
public interface InputListener {

    void onPress(MotionEvent event, int index);
    void onMove(MotionEvent event, int index);
    void onRelease(MotionEvent event, int index);

}
