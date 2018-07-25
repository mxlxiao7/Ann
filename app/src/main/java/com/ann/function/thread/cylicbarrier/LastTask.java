package com.ann.function.thread.cylicbarrier;

import android.util.Log;
import com.ann.utils.Utils;
import java.util.Random;


public class LastTask extends Thread {
    public void run() {
        Utils.msg("......终于要执行最后的任务了......");

        //模拟耗时的任务
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        Utils.msg("......最终任务执行完毕......");
    }
}
