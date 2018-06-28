package com.ann;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;

import com.ann.config.Global;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class AnnApplication extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Global.isDebug()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        }

        registerActivityLifecycleCallbacks(mLifecycleCallbacks);

        /**
         * 初始化logger
         */
        Logger.addLogAdapter(new AndroidLogAdapter());


        Logger.i(this.getClass().getSimpleName() + ":" + new Exception().getStackTrace()[0].getMethodName());
    }



    private ActivityLifecycleCallbacks mLifecycleCallbacks = new ActivityLifecycleCallbacks(){

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };


}
