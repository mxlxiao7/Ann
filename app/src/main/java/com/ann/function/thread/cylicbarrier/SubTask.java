package com.ann.function.thread.cylicbarrier;

import android.util.Log;

import com.ann.utils.Utils;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;


public class SubTask extends Thread {

    private String name;
    private CyclicBarrier cb;

    public SubTask(String name, CyclicBarrier cb) {
        this.name = name;
        this.cb = cb;
    }

    public void run() {
        Utils.msg("[并发任务-" + name + "]  开始执行");


        //模拟耗时的任务
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        try {
            Utils.msg("[并发任务-" + name + "]  等待 && 通知障碍器");
            //每执行完一项任务就通知障碍器
            cb.await();
        } catch (Exception e) {
            Utils.msg(Log.getStackTraceString(e));
        }

        /**
         * 这里与CountDownLatch 和 Semaphore 区别是子任务和主任务可以在到达栅栏后,在继续执行后面的内容
         */
        Utils.msg("[并发任务-" + name + "]  任务完成");
    }
}
