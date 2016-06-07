package com.tjgs.robotevolution;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainSurfaceView extends GLSurfaceView {

    private GLRenderer mainRenderer;

	public MainSurfaceView(Context context) {
		super(context);
        setEGLContextClientVersion(2);
        mainRenderer = new GLRenderer(context);

        setRenderer(mainRenderer);

	}

    @Override
	public boolean onTouchEvent(MotionEvent event){
        return mainRenderer.onTouchEvent(event);
    }
	
}
