package com.ann.function.thread.phaser;

import com.ann.utils.Utils;


/**
 * Phaser是一个更强大的、更复杂的同步辅助类，可以代替CyclicBarrier CountDownLatch的功能，但是比他们更强大,
 * 1、类机制是在每一步结束的位置对线程进行同步，当所有的线程都完成了这一步，才能进行下一步
 * 2、phaser可以动态的增减参与量
 * <p>
 * 使用phaser.arriveAndAwaitAdvance(); //等待参与者达到指定数量，才开始运行下面的代码
 * 使用phaser.arriveAndDeregister(); //注销当前线程，该线程就不会进入休眠状态，也会从phaser的数量中减少
 * <p>
 * 原理:AQS的实现
 */
public class PhaserDemo {

    public static void main() {
        new PhaserDemo().doAction();
    }

    /**
     * 开启3个线程，分别打印字母a,b,c各10次，然后进入下一阶段打印后面的字母d,e,f各10次，然后再进入下一阶段.......以此类推，直到整个字母表全部打印完毕。
     */
    public void doAction() {
        java.util.concurrent.Phaser phaser = new java.util.concurrent.Phaser(3) {// 共有3个工作线程，因此在构造函数中赋值为3

            /**
             *
             * @param phase
             * @param registeredParties
             * @return
             */
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                Utils.msg("\n=========华丽的分割线============= " + phase);
                return registeredParties == 0;
            }
        };

        Utils.msg("程序开始执行");

        char a = 'a';
        for (int i = 0; i < 3; i++) { // 创建并启动3个线程
            new PrintThread((char) (a + i), phaser).start();
        }

        while (!phaser.isTerminated()) {// 只要phaser不终结，主线程就循环等待
            Utils.msg("Thread.yield()");
            Thread.yield();
        }

        Utils.msg("程序结束");
    }
}
