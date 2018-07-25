package com.ann.function.thread.threadpool;


import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * app线程管理类，管理整个app线程工作
 * 1、
 * Author:maxiaolong
 * Date:2018/7/14
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class ThreadManager implements Serializable {

    private static final String TAG = ThreadManager.class.getSimpleName();

    /**
     * 单例的构造函数必须私有
     */
    private ThreadManager() {
        //防止反射
        if (null != getInstance()) {
            throw new RuntimeException("单例模式，拒绝反射创建");
        }


    }

    private static class Inner {
        public static final ThreadManager INSTANCE = new ThreadManager();
    }


    public static ThreadManager getInstance() {
        return Inner.INSTANCE;
    }


    /**
     * 防止反序列化获取多个对象的漏洞。
     * 无论是实现Serializable接口，或是Externalizable接口，当从I/O流中读取对象时，readResolve()方法都会被调用到。
     * 实际上就是用readResolve()中返回的对象直接替换在反序列化过程中创建的对象。
     *
     * @return
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }


    private static ThreadPoolProxy poolProxy;


    public static ThreadPoolProxy getPoolProxy() {
        if (poolProxy == null) {
            synchronized (TAG) {
                if (poolProxy == null) {
                    int processorCount = Runtime.getRuntime().availableProcessors();
                    int maxAvailable = Math.max(processorCount * 3, 10);
                    // 线程池的核心线程数、最大线程数，以及keepAliveTime都需要根据项目需要做修改
                    // PS：创建线程的开销 高于 维护线程(wait)的开销
                    poolProxy = new ThreadPoolProxy(processorCount, maxAvailable, 15000);
                }
            }
        }
        return poolProxy;
    }

}
