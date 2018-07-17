package com.ann.function.android;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 *
 */
public class LanguageSettings {


    public String getCurrentLanguage(Context c) {
        try {
            return c.getResources().getConfiguration().locale.getCountry();
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 刷新语言
     */
    public static void updateActivity(Context c, String sta) {
        if (c == null) {
            return;
        }
        try {
            // 本地语言设置
            Resources res = c.getResources();
            Configuration conf = res.getConfiguration();
            conf.locale = new Locale(sta);
            res.updateConfiguration(conf, res.getDisplayMetrics());
        } catch (Exception e) {

        }
    }


    /**
     * 恢复为系统默认语言
     */
    public static void restoreSystemLanguage(Context c) {
        if (c == null) {
            return;
        }

        try {
            Resources res = c.getResources();
            Configuration conf = res.getConfiguration();
            conf.locale = Locale.getDefault();
            res.updateConfiguration(res.getConfiguration(), res.getDisplayMetrics());
        } catch (Exception e) {

        }
    }


}
