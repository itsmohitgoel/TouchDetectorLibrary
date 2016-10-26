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
    private GestureDetector mGestureDetector;
    private Activity activity = null;
    JSONArray touchDataArray = new JSONArray();
    private long startTime;

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
                    touchJson.put(activity.getString(R.string.activity_name), activity.getClass().getSimpleName());
                    touchJson.put(activity.getString(R.string.uuid), Utility.getDeviceId(activity));
                    touchJson.put(activity.getString(R.string.gesture_type_key), activity.getString(R.string.gesture_type_touch_value));
                    touchJson.put(activity.getString(R.string.start_time_key), Utility.getFormattedTime(startTime));
                    touchJson.put(activity.getString(R.string.end_time_key), Utility.getFormattedTime(System.currentTimeMillis()));
                    touchJson.put(activity.getString(R.string.touch_data_key), touchDataArray);
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

