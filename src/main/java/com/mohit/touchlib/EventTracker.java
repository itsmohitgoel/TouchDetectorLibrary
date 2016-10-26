package com.mohit.touchlib;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Mohit on 25-10-2016.
 */
public class EventTracker {
    private static EventTracker instance = null;
    private ArrayList<Activity> activityList;

    private EventTracker() {
        activityList = new ArrayList<>();
    }

    public static EventTracker getInstance() {
        if (instance == null) {
            synchronized (EventTracker.class) {
                if (instance == null) {
                    instance = new EventTracker();
                }
            }
        }

        return instance;
    }

    public boolean register(Activity activity) {
        View v = activity.getWindow().getDecorView();

        SwipeListener swipeListener = new SwipeListener(activity);
        GestureDetector gestureDetector = new GestureDetector(activity, swipeListener);

        TouchListener touchListener = new TouchListener(gestureDetector);
        touchListener.setActivityName(activity.getClass().getSimpleName());
        touchListener.setUUID(Utility.getDeviceId(activity));
//        gestureDetector.setOnDoubleTapListener(swipeListener);
//        gestureDetector.seton(swipeListener);
        v.setOnTouchListener(touchListener);
        return activityList.add(activity);
    }

    public boolean unregister(Activity activity) {
        if (activityList.contains(activity)) {
            return activityList.remove(activity);
        }
        return false;
    }
}
