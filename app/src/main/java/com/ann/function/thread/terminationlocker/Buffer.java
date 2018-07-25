package com.ann.function.thread.terminationlocker;

import com.ann.utils.Utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by maxiaolong on 2017/1/13.
 */
public class Buffer {
    private Lock locker = new ReentrantLock();

    public Buffer() {

    }

    public void write() {
        locker.lock();
        try {
            long startTime = System.currentTimeMillis();
            Utils.msg("Writer:开始往这个buff写入数据…");
            for (; ; ) {// 模拟要处理很长时间
                if (System.currentTimeMillis() - startTime > 10 * 1000) {
                    break;
                }
            }
            Utils.msg("Writer:终于写完了");
        } finally {
            locker.unlock();
        }
    }

    public void read() throws InterruptedException {
        locker.lockInterruptibly();// 注意这里，可以响应中断
        try {
            Utils.msg("Reader:从这个buff读数据");
        } finally {
            locker.unlock();
        }
    }
}
