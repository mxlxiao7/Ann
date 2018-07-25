package com.ann.function.thread.reentrantlock;

import com.ann.utils.Utils;

/**
 * Created by maxiaolong on 2017/1/13.
 */

public class P extends Thread {
    private ProductQueue<String> queue;      // queue

    public P(ProductQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                String msg = "姓名--" + i + "内容--" + i;
                Utils.msg("[消费者]  " + msg);
                this.queue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
