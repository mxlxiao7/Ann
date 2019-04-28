package com.ann.utils;

import com.orhanobut.logger.Logger;


/**
 * @author leon
 * @date 2019-04-26 22:59
 */
public class LogUtil {

    private static final String TAG = "Ann";

    static {
        Logger.t(TAG);
    }


    public static void v(String msg) {
        Logger.v(msg);
    }


    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }


    public static void w(String msg) {
        Logger.w(msg);
    }


    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(Throwable tr) {
        Logger.e(tr, "message:");
    }
}
