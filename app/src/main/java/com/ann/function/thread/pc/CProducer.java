package com.ann.function.thread.pc;

/**
 * Created by maxiaolong on 2017/1/13.
 */

public class CProducer extends Thread {
    private CInfo info;      // 保存Info引用

    public CProducer(CInfo info) {
        this.info = info;
    }

    public void run() {
        boolean flag = true;   // 定义标记位
        for (int i = 0; i < 100; i++) {
            this.info.set("姓名--" + i, "内容--" + i);    // 设置名称
        }
    }
}
