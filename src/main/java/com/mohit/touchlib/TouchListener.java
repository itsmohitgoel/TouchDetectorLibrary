package com.mohit.touchlib;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mohit on 25-10-2016.
 */

public class TouchListener implements View.OnTouchListener{
    private String activityName = "";
    private String UUID = "";
    private GestureDetector mGestureDetector;

    public TouchListener(GestureDetector gestureDetector){
        mGestureDetector = gestureDetector;
    }
    public static final String LOG_TAG = TouchListener.class.getSimpleName();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        int action = event.getAction();
        Log.i(LOG_TAG, "Touch at: X= " + event.getX() + ",  Y = " + event.getY() + ", in: " + activityName
            + ", time:  " + System.currentTimeMillis() );
        return false;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}

