package com.ann.function.threadmanager;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * app线程管理类，管理整个app线程工作
 * 1、通过代理类配置自定义线程池
 *
 * 知识点：
 * execute与submit的不同
 * 1、返回结果不同   execute没有返回结果  submit有返回结果Future
 * 2、异常控制不同   execute会抛出异常    submit不会抛出异常，除非调用Future.get()
 *
 *
 * <p>
 * Author:maxiaolong
 * Date:2018/7/14
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class ThreadPoolProxy {

    private static final String TAG = ThreadPoolProxy.class.getSimpleName();

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 线程池中核心线程数
     */
    private int corePoolSize;

    /**
     * 线程池中最大线程数，若并发数高于该数，后面的任务则会等待
     */
    private int maximumPoolSize;

    /**
     * 超出核心线程数的线程在执行完后保持alive时长
     */
    private int keepAliveTime;

    /**
     * @param keepAliveTime time in milliseconds
     */
    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, int keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    /**
     * 任务添加到线程池
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        } else {
            check();
            threadPoolExecutor.execute(runnable);
        }
    }

    /**
     * 任务添加到线程池
     *
     * @param callable
     */
    public <T> Future<T> execute(Callable<T> callable) {
        if (callable == null) {
            return null;
        } else {
            check();
            return threadPoolExecutor.submit(callable);
        }
    }


    /**
     * 检查线程池是否存在或者已经停止
     */
    private void check() {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            synchronized (TAG) {
                if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                    threadPoolExecutor = createExecutor();
                    threadPoolExecutor.allowCoreThreadTimeOut(false); // 核心线程始终不消失
                }
            }
        }
    }

    /**
     * 创建自定义线程池
     */
    private ThreadPoolExecutor createExecutor() {
        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "ann-threadpool-"),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
