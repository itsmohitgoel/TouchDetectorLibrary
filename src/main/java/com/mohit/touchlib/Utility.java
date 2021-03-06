package com.mohit.touchlib;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Mohit on 26-10-2016.
 */
public class Utility {
    public static String getDeviceId(Context context) {
        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return deviceId;
        } else {
            return android.os.Build.SERIAL;
        }
    }

    public static String getFormattedTime(long timeInMillis) {
        String date = new java.text.SimpleDateFormat("hh:mm:ss a").format(new java.util.Date(timeInMillis));
        return date;
    }
}
