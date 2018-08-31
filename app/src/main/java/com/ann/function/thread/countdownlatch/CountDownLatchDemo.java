package com.ann.function.thread.countdownlatch;

import android.util.Log;

import com.ann.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;


/**
 * 主线任务阻塞等待许多前置条件任务执行完毕，再执行主线任务
 * <p>
 * 原理:AQS的实现
 * 给state设置值，每当一个任务执行完毕调用countDown()方法，这个值就减一，当最后为0时，主任务获取到执行权限，开始执行
 */
public class CountDownLatchDemo {
    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static void main() throws InterruptedException {
        Utils.msg("主任务开始执行 ");

        CountDownLatch latch = new CountDownLatch(2);//两个工人的协作
        Worker worker1 = new Worker("zhang san", 5000, latch);
        Worker worker2 = new Worker("li si", 8000, latch);
        worker1.start();//
        worker2.start();//

        Utils.msg("主任务开始等待 ");
        latch.await();//等待所有工人完成工作
        Utils.msg("所有任务执行完毕 " + sdf.format(new Date()));
    }


    static class Worker extends Thread {
        String workerName;
        int workTime;
        java.util.concurrent.CountDownLatch latch;

        public Worker(String workerName, int workTime, java.util.concurrent.CountDownLatch latch) {
            this.workerName = workerName;
            this.workTime = workTime;
            this.latch = latch;
        }

        public void run() {
            Utils.msg("Worker " + workerName + " do work begin at " + sdf.format(new Date()));
            doWork();//工作了
            Utils.msg("Worker " + workerName + " do work complete at " + sdf.format(new Date()));
            latch.countDown();//工人完成工作，计数器减一
        }

        private void doWork() {
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                Utils.msg(Log.getStackTraceString(e));
            }
        }
    }


}