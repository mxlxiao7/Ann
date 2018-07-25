package com.ann.function.thread.threadpool;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂类
 * 作用：
 * 1、定义统一的线程名称
 * <p>
 * Author:maxiaolong
 * Date:2018/7/14
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class DefaultThreadFactory implements ThreadFactory {

    /**
     * 线程池的计数
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    /**
     * 线程的计数
     */
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    /**
     * 线程组
     */
    private final ThreadGroup group;
    /**
     * 线程前缀
     */
    private final String namePrefix;
    /**
     * 线程优先级
     */
    private final int threadPriority;

    /**
     * @param threadPriority   线程优先级
     * @param threadNamePrefix 线程前缀
     */
    public DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
        this.threadPriority = threadPriority;
        this.group = Thread.currentThread().getThreadGroup();
        this.namePrefix = threadNamePrefix + POOL_NUMBER.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(
                group,
                runnable,
                namePrefix + THREAD_NUMBER.getAndIncrement(),
                0
        );
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        thread.setPriority(threadPriority);
        return thread;
    }
}
