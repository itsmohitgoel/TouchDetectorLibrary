package com.mohit.touchlib;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohit on 25-10-2016.
 */
class SwipeListener implements GestureDetector.OnGestureListener {
    private Activity activity = null;

    public SwipeListener(Activity activity) {
        this.activity = activity;
    }

    public static final String LOG_TAG = SwipeListener.class.getSimpleName();

    @Override
    public boolean onDown(MotionEvent e) {
//        Log.i(LOG_TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Log.i(LOG_TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(LOG_TAG, "onSingleTapUp");
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
        JSONObject swipeJson = new JSONObject();
        try {
            swipeJson.put(activity.getString(R.string.activity_name), activity.getClass().getSimpleName());
            swipeJson.put(activity.getString(R.string.uuid), Utility.getDeviceId(activity));
            swipeJson.put(activity.getString(R.string.gesture_type_key), activity.getString(R.string.gesture_type_swipe_value));
            swipeJson.put(activity.getString(R.string.start_time_key), "");
            swipeJson.put(activity.getString(R.string.end_time_key), "");

            JSONArray swipeDataArray = new JSONArray();
            JSONObject jsonEvent1 = new JSONObject();
            jsonEvent1.put(activity.getString(R.string.x_key), e1.getX());
            jsonEvent1.put(activity.getString(R.string.y_key), e1.getY());

            JSONObject jsonEvent2 = new JSONObject();
            jsonEvent2.put(activity.getString(R.string.x_key), e2.getX());
            jsonEvent2.put(activity.getString(R.string.y_key), e2.getY());
            swipeDataArray.put(jsonEvent1).put(jsonEvent2);


            swipeJson.put(activity.getString(R.string.swipe_data_key), swipeDataArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Left To Right swipe
        if ((e1.getX() < e2.getX()) && (e2.getX() - e1.getX()) > 100) {
            if (swipeJson != null) {
                try {
                    swipeJson.accumulate(activity.getString(R.string.swipe_direction_key),
                            activity.getString(R.string.swipe_direction_l_to_r_value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //Right to left swipe
        if ((e1.getX() > e2.getX()) && (e1.getX() - e2.getX()) > 100) {
            if (swipeJson != null) {
                try {
                    swipeJson.accumulate(activity.getString(R.string.swipe_direction_key),
                            activity.getString(R.string.swipe_direction_r_to_l_value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        // Top to bottom swipe
        if ((e1.getY() < e2.getY()) && (e2.getY() - e1.getY()) > 100) {
            if (swipeJson != null) {
                try {
                    swipeJson.accumulate(activity.getString(R.string.swipe_direction_key),
                            activity.getString(R.string.swipe_direction_t_to_b_value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        // Bottom to top swipe
        if ((e1.getY() > e2.getY()) && (e1.getY() - e2.getY()) > 100) {
            if (swipeJson != null) {
                try {
                    swipeJson.accumulate(activity.getString(R.string.swipe_direction_key),
                            activity.getString(R.string.swipe_direction_b_to_t_value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        Log.d(LOG_TAG, swipeJson.toString());
        swipeJson = null;
        return true;
    }


}

