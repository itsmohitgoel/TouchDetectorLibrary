package com.mohit.touchlib;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/** Handler class for monitoring swipe gesture to the respective
 * views or activities
 * Created by Mohit on 25-10-2016.
 */
class SwipeListener implements GestureDetector.OnGestureListener {
    WeakReference<Activity> mParentActivity;
    private long startTime;

    public SwipeListener(WeakReference<Activity> parent) {
        this.mParentActivity = parent;
    }

    public static final String LOG_TAG = SwipeListener.class.getSimpleName();

    @Override
    public boolean onDown(MotionEvent e) {
        startTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Log.i(LOG_TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        Log.i(LOG_TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        Log.i(LOG_TAG, "onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        Log.i(LOG_TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Activity activity = mParentActivity.get();
        if (activity == null) {
            return true; //Activity has been garbage collected, so return
        }

        JSONObject swipeJson = new JSONObject();
        try {
            swipeJson.put("activity_name", activity.getClass().getSimpleName());
            swipeJson.put("UUID", Utility.getDeviceId(activity));
            swipeJson.put("gesture_type", "swipe");
            swipeJson.put("start_time", Utility.getFormattedTime(startTime));
            swipeJson.put("end_time", Utility.getFormattedTime(System.currentTimeMillis()));

            JSONArray swipeDataArray = new JSONArray();
            JSONObject jsonEvent1 = new JSONObject();
            jsonEvent1.put("X", e1.getX());
            jsonEvent1.put("Y", e1.getY());

            JSONObject jsonEvent2 = new JSONObject();
            jsonEvent2.put("X", e2.getX());
            jsonEvent2.put("Y", e2.getY());
            swipeDataArray.put(jsonEvent1).put(jsonEvent2);
            addDirections(e1, e2, swipeJson, activity);
            swipeJson.put("swipe_data", swipeDataArray);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error occurred while building json object for swipe data.");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, swipeJson.toString());
        swipeJson = null;


        return true;
    }

    /**
     * Helper method to add directions of the swipe movement, to the json object
     */
    private void addDirections(MotionEvent e1, MotionEvent e2, JSONObject swipeJson, Activity activity) throws JSONException {
        //Left To Right swipe
        if ((e1.getX() < e2.getX()) && (e2.getX() - e1.getX()) > 100) {
            if (swipeJson != null) {
                swipeJson.accumulate("direction",
                        "Left To Right");
            }
        }

        //Right to left swipe
        if ((e1.getX() > e2.getX()) && (e1.getX() - e2.getX()) > 100) {
            if (swipeJson != null) {
                swipeJson.accumulate("direction",
                        "Right To Left");
            }

        }

        // Top to bottom swipe
        if ((e1.getY() < e2.getY()) && (e2.getY() - e1.getY()) > 100) {
            if (swipeJson != null) {
                swipeJson.accumulate("direction",
                        "Top To Bottom");
            }
        }

        // Bottom to top swipe
        if ((e1.getY() > e2.getY()) && (e1.getY() - e2.getY()) > 100) {
            if (swipeJson != null) {
                swipeJson.accumulate("direction",
                        "Bottom To Top");
            }
        }
    }
}

