package com.ann.function.thread.reentrantlock;

import com.ann.utils.Utils;

/**
 * Created by maxiaolong on 2017/1/13.
 */

public class C extends Thread {
    private ProductQueue<String> queue;      // queue

    public C(ProductQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Utils.msg("[生产者]  " + this.queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
