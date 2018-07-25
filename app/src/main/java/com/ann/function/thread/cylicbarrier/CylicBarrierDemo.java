package com.ann.function.thread.cylicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 原理:ReentrantLock + AQS的实现
 * <p>
 * 举例：比如旅游场景，要等大家集合到一起，然后在一起想目的地出发
 */
public class CylicBarrierDemo {

    public static void main() {
        new CylicBarrierDemo().doAction();
    }

    public void doAction() {
        CyclicBarrier cb = new CyclicBarrier(5, new LastTask());
        new SubTask("A", cb).start();
        new SubTask("B", cb).start();
        new SubTask("C", cb).start();
        new SubTask("D", cb).start();
        new SubTask("E", cb).start();
    }
}
