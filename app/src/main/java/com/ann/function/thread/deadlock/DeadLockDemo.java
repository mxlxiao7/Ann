package com.ann.function.thread.deadlock;


import com.ann.utils.Utils;

/**
 * 当一个线程到达exchange调用点时，如果它的伙伴线程此前已经调用了此方法，那么它的伙伴会被调度唤醒并与之进行对象交换，
 * 然后各自返回。如果它的伙伴还没到达交换点，那么当前线程将会被挂起，直至伙伴线程到达——完成交换正常返回；
 * 或者当前线程被中断——抛出中断异常；又或者是等候超时——抛出超时异常。
 * <p>
 * 原理:
 */
public class DeadLockDemo {

    public static void main() {
        Object locka = new Object();
        Object lockb = new Object();

        new Thread(new SyncThread(locka, lockb)).start();
        new Thread(new SyncThread(lockb, locka)).start();
    }
}


class SyncThread implements Runnable {
    private Object obj1;
    private Object obj2;

    public SyncThread(Object obj1, Object obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void run() {
        work();
    }

    private void work() {
        try {
            for (int i = 0; i < 1000; i++) {
                String name = Thread.currentThread().getName();
                Utils.msg(name + " working... " + i);
                synchronized (obj1) {
                    Thread.sleep(10);
                    synchronized (obj2) {
                        System.out.println(name + "  " + obj2);
                        Thread.sleep(1 * 1000);
                    }
                }
                Utils.msg(name + " finished execution.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}