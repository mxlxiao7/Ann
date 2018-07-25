package com.ann.function.thread.semaphore;


import android.util.Log;

import com.ann.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 控制同时执行任务数量
 * 原理:AQS的实现
 * <p>
 * 10名学生阅读2本书，因为书的数量是固定的，所以只有2名学生可以持有书阅读，其他人需要等别人看完再看。
 */
public class SemaphoreDemo {

    /**
     * 采用新特性来启动和管理线程——内部使用线程池
     */
    private ExecutorService exec = Executors.newCachedThreadPool();

    /**
     * 只允许2个线程同时访问
     */
    private java.util.concurrent.Semaphore semp = new java.util.concurrent.Semaphore(2);


    public static void main() {
        new SemaphoreDemo().doAction();
    }

    public void doAction() {
        //模拟10个人
        for (int index = 0; index < 10; index++) {
            final int name = index + 1;
            Runnable run = () -> {
                try {
                    //获取许可
                    semp.acquire();
                    Utils.msg("学生 " + name + " 开始读书");
                    //模拟耗时的任务
                    Thread.sleep(5000);
                    //释放许可
                    semp.release();
//                    Utils.msg("学生 " + Thread.currentThread().getName() + "读书完毕：");
                } catch (InterruptedException e) {
                    Utils.msg(Log.getStackTraceString(e));
                }
            };
            exec.execute(run);
        }
    }
}
