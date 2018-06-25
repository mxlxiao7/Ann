package com.ann.config;


import com.ann.BuildConfig;

public class Global {

    public static final boolean DEBUG = false;


    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}