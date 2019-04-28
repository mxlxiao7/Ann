package com.ann;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ann.config.Global;
import com.ann.utils.LogUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class AnnApplication extends Application {
    private RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        if (Global.isDebug()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        }

        //内存检测
        refWatcher = setupLeakCanary();

        //注册声明周期回调
        registerActivityLifecycleCallbacks(mLifecycleCallbacks);

        /**
         * 初始化logger
         */
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(this.getClass().getSimpleName() + ":" + new Exception().getStackTrace()[0].getMethodName());
    }


    /**
     * 初始化LeakCanary
     *
     * 为什么要检测当前进程?
     * 答:
     *
     * @return
     */
    public RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }


    /**
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        return ((AnnApplication) context.getApplicationContext()).refWatcher;
    }


    private ActivityLifecycleCallbacks mLifecycleCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Logger.d(activity.getClass().getSimpleName() + " onActivityCreated()");
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
            Logger.d(activity.getClass().getSimpleName() + " onActivityDestroyed()");
        }
    };

}
