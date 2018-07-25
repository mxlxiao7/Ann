package com.ann.function.thread.phaser;

import android.util.Log;

import com.ann.utils.Utils;

import java.util.concurrent.Phaser;

/**
 *
 * Created by maxiaolong on 2017/1/16.
 */
public class PrintThread extends Thread {

    private char c;
    private Phaser phaser;

    public PrintThread(char c, Phaser phaser) {
        this.c = c;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            for (int i = 0; i < 3; i++) { // 将当前字母打印10次
                Utils.msg(c + " ");
            }
            // 打印完当前字母后，将其更新为其后第三个字母，例如b更新为e，用于下一阶段打印
            c = (char) (c + 3);
            if (c > 'z') {
                // 如果超出了字母z，则在phaser中动态减少一个线程，并退出循环结束本线程
                phaser.arriveAndDeregister();
                break;
            } else {
                // 反之，等待其他线程到达阶段终点，再一起进入下一个阶段
                phaser.arriveAndAwaitAdvance();
            }
        }
    }
}
