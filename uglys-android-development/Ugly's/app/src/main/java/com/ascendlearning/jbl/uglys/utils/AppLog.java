package com.ascendlearning.jbl.uglys.utils;

import android.util.Log;


public class AppLog {

    private static boolean mLogEnabled = true;

    public static void i(String tag, String message) {
        if (mLogEnabled)
            Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (mLogEnabled)
            Log.e(tag, message);
    }

    public static void w(String tag, String message) {
        if (mLogEnabled)
            Log.w(tag, message);
    }

    public static void v(String tag, String message) {
        if (mLogEnabled)
            Log.v(tag, message);
    }

    public static void d(String tag, String message) {
        if (mLogEnabled)
            Log.d(tag, message);
    }

    public static boolean isEnabled() {
        return mLogEnabled;
    }


}
