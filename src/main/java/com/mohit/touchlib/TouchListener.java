package com.mohit.touchlib;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohit on 25-10-2016.
 */

public class TouchListener implements View.OnTouchListener {
    public static final String LOG_TAG = TouchListener.class.getSimpleName();
    private String activityName = "";
    private String UUID = "";
    private GestureDetector mGestureDetector;
    private Activity activity = null;
    JSONArray touchDataArray = new JSONArray();

    public TouchListener(GestureDetector gestureDetector, Activity activity) {
        mGestureDetector = gestureDetector;
        this.activity = activity;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            JSONObject jsonEvent = new JSONObject();
            jsonEvent.put(activity.getString(R.string.x_key), event.getX());
            jsonEvent.put(activity.getString(R.string.y_key), event.getY());

            touchDataArray.put(jsonEvent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String actionString = "";


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionString = "DOWN";
                break;
            case MotionEvent.ACTION_UP:
                actionString = "UP";

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionString = "PNTR DOWN";
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionString = "PNTR UP";
                break;
            case MotionEvent.ACTION_MOVE:
                actionString = "MOVE";
                break;
            default:
                actionString = "";
        }


        mGestureDetector.onTouchEvent(event);
        int action = event.getAction();
        Log.i(LOG_TAG, "Touch at: X= " + event.getX() + ",  Y = " + event.getY() + ", in: " + activityName
                + ", time:  " + System.currentTimeMillis() + ", actiontype : " + actionString);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(LOG_TAG, touchDataArray.toString());
            touchDataArray = new JSONArray();
        }
        return false; //this listener has not consumed the event, so that it can be relayed  to GestureDetector
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}

