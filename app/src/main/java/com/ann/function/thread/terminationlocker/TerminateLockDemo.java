package com.ann.function.thread.terminationlocker;

import com.ann.utils.Utils;

/**
 * 演示读取数据时超时打断
 */
public class TerminateLockDemo {

    public static void main() {
        new TerminateLockDemo().doAction();
    }


    public void doAction() {
        Buffer buff = new Buffer();
        final Writer writer = new Writer(buff);
        final Reader reader = new Reader(buff);
        writer.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        reader.start();
        new Thread(() -> {
            long start = System.currentTimeMillis();
            for (; ; ) {
                if (System.currentTimeMillis() - start > 5 * 1000) {
                    Utils.msg("Reader:不等了，尝试中断");
                    reader.interrupt();  //此处中断读操作
                    break;
                }
            }
        }).start();
    }
}
