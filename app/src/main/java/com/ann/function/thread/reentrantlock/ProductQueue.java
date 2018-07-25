package com.ann.function.thread.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者 - 消费者 数据队列
 * <p>
 * 终点学习Condition的用法
 * 1、条件变量很大一个程度上是为了解决Object.wait/notify/notifyAll难以使用的问题
 * 2、每一个Lock可以有任意数据的Condition对象，Condition是与Lock绑定的，所以就有Lock的公平性特性：如果是公平锁，
 * 线程为按照FIFO的顺序从Condition.await中释放，如果是非公平锁，那么后续的锁竞争就不保证FIFO顺序了。
 * <p>
 * Author:maxiaolong
 * Date:2018/8/01
 * Email:mxlxiao7@sina.com
 */
public class ProductQueue<T> {
    private final T[] items;
    private final Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private int head;
    private int tail;
    private int count;

    public ProductQueue() {
        //创建泛型数组
        this(10);
    }

    public ProductQueue(int maxSize) {
        //创建泛型数组
        items = (T[]) new Object[maxSize];
    }

    /**
     * 传入数据
     *
     * @param t
     */
    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            //队列如果已经满载，则挂起生产者线程
            while (count == getCapacity()) {
                notFull.await();
            }

            //添加队列
            items[tail] = t;
            if (++tail == getCapacity()) {
                tail = 0;
            }

            //计数增加
            count++;

            //通知挂起的消费者线程
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            //队列如果已为空，则挂起消费者线程
            while (count == 0) {
                notEmpty.await();
            }

            T t = items[head];
            items[head] = null;//GC

            //重置头索引
            if (++head == getCapacity()) {
                head = 0;
            }

            //计数减少
            count--;

            //唤醒生成者线程
            notFull.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }


    /**
     * 获取当前
     *
     * @return
     */
    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取容量大小
     *
     * @return
     */
    public int getCapacity() {
        return items.length;
    }
}
