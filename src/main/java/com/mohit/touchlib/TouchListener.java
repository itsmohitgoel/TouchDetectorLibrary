package com.mohit.touchlib;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Mohit on 25-10-2016.
 */

public class TouchListener implements View.OnTouchListener {
    public static final String LOG_TAG = TouchListener.class.getSimpleName();
    private GestureDetector mGestureDetector;
    WeakReference<Activity> mParentActivity;
    JSONArray touchDataArray = new JSONArray();
    private long startTime;

    public TouchListener(GestureDetector gestureDetector, WeakReference<Activity> parent) {
        mGestureDetector = gestureDetector;
        this.mParentActivity = parent;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Activity activity = mParentActivity.get();
        if (activity == null) {
            return true; //Activity has been garbage collected, so return and also don't relay event
        }

        try {
            JSONObject jsonEvent = new JSONObject();
            jsonEvent.put("X", event.getX());
            jsonEvent.put("Y", event.getY());

            touchDataArray.put(jsonEvent);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error occurred while adding touch coordinates to touch data json");
            e.printStackTrace();
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                JSONObject touchJson = new JSONObject();
                try {
                    touchJson.put("activity_name", activity.getClass().getSimpleName());
                    touchJson.put("UUID", Utility.getDeviceId(activity));
                    touchJson.put("gesture_type", "touch");
                    touchJson.put("start_time", Utility.getFormattedTime(startTime));
                    touchJson.put("end_time", Utility.getFormattedTime(System.currentTimeMillis()));
                    touchJson.put("touch_data", touchDataArray);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error occurred while building json object for touch data.");
                    e.printStackTrace();
                }

                Log.d(LOG_TAG, touchJson.toString());
                touchDataArray = new JSONArray();
                break;
        }

        mGestureDetector.onTouchEvent(event);
        return false; //this listener has not consumed the event, so that it can be relayed  to GestureDetector
    }
}

