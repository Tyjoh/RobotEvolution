package com.tjgs.robotevolution;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

public class GLRenderer implements Renderer{

    private static final String TAG = GLRenderer.class.getSimpleName();

    //screen dimensions
	private int width, height;

    //variables for calculating fps and delta time
    private long lastTime;
	private int fpsCount;
	private int fps;

    //the main game controller object
	private Game game;

    //app context
	private Context context;
	
	public GLRenderer(Context context) {
		width = -1;
		height = -1;
		lastTime = System.currentTimeMillis();
		fpsCount = 0;
        this.context = context;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		width = -1;
		height = -1;

		game = new Game(context);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;

		game.screenSizeChanged(width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
        //TODO: calculate proper delta time value
		float dx = 1/60f;

		game.update(dx);
		game.render();
		
		fpsCount++;
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime >= 1000){
			fps = fpsCount;
			fpsCount = 0;
			lastTime = currentTime;
            //Log.d(TAG, "FPS: " + fps);
        }
	}

	public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        int index = MotionEventCompat.getActionIndex(event);

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                        game.onPress(event, index);
                    return true;

                case MotionEvent.ACTION_POINTER_DOWN:
                        game.onPress(event, index);

                    return true;

                case MotionEvent.ACTION_MOVE:
                        game.onMove(event, index);
                    return true;

                case MotionEvent.ACTION_UP:
                        game.onRelease(event, index);
                    return true;

                case MotionEvent.ACTION_POINTER_UP:
                        game.onRelease(event, index);
                    return true;
            }

		return true;
	}
	
	public int getFps(){
		return fps;
	}

}
