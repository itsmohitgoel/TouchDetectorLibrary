package com.mohit.touchlib;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Singleton class to provide register activities for
 * touch events monitoring
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

    public TouchListener register(Activity activity) {
        View v = activity.getWindow().getDecorView();

        SwipeListener swipeListener = new SwipeListener(new WeakReference<Activity>(activity));
        GestureDetector gestureDetector = new GestureDetector(activity, swipeListener);

        TouchListener touchListener = new TouchListener(gestureDetector, new WeakReference<Activity>(activity));
        v.setOnTouchListener(touchListener);
        return touchListener;
    }

    public boolean unregister(Activity activity) {
        if (activityList.contains(activity)) {
            return activityList.remove(activity);
        }
        return false;
    }
}
