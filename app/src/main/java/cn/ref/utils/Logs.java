package cn.ref.utils;

import android.util.Log;

public class Logs {
    private static final String TAG = "sanbo";


    public static void v(Throwable e) {
        v(Log.getStackTraceString(e));
    }

    public static void d(Throwable e) {
        d(Log.getStackTraceString(e));
    }

    public static void i(Throwable e) {
        i(Log.getStackTraceString(e));
    }

    public static void w(Throwable e) {
        w(Log.getStackTraceString(e));
    }

    public static void e(Throwable e) {
        e(Log.getStackTraceString(e));
    }

    public static void wtf(Throwable e) {
        wtf(Log.getStackTraceString(e));
    }


    public static void v(String info) {
        println(Log.VERBOSE, TAG, info);
    }

    public static void d(String info) {
        println(Log.DEBUG, TAG, info);
    }

    public static void i(String info) {
        println(Log.INFO, TAG, info);
    }

    public static void w(String info) {
        println(Log.WARN, TAG, info);
    }

    public static void e(String info) {
        println(Log.ERROR, TAG, info);
    }

    public static void wtf(String info) {
        println(Log.ASSERT, TAG, info);
    }




    public static int println(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }
}
