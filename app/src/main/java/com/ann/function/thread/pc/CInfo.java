package com.ann.function.thread.pc;

import com.ann.utils.Utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CInfo {

    private String name;
    private String content;
    private boolean flag = true;
    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();
    private long sleepTime = 3 * 10;


    public void set(String name, String content) {
        locker.lock();
        try {
            while (!flag) {
                //这里挂起线程释放锁
                condition.await();
            }
            this.name = name;    // 设置名称
            Thread.sleep(sleepTime);
            this.content = content;  // 设置内容
            flag = false; // 改变标志位，表示可以取走
            condition.signal();
        } catch (Exception e) {

        } finally {
            locker.unlock();
        }
    }


    public void get() {
        locker.lock();
        try {
            while (flag) {
                //这里挂起线程释放锁
                condition.await();
            }
            Thread.sleep(sleepTime);
            Utils.msg(this.name + " --> " + content);
            flag = true;  // 改变标志位，表示可以生产
            condition.signal();
        } catch (Exception e) {

        } finally {
            locker.unlock();
        }
    }


}
