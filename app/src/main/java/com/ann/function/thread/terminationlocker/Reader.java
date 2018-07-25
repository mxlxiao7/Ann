package com.ann.function.thread.terminationlocker;


import com.ann.utils.Utils;

public class Reader extends Thread {

    private Buffer buff;

    public Reader(Buffer buff) {
        this.buff = buff;
    }

    @Override
    public void run() {
        try {
            buff.read();//可以收到中断的异常，从而有效退出
        } catch (InterruptedException e) {
            Utils.msg("Reader:我不读了");
        }
        Utils.msg("Reader:读结束");
    }
}
