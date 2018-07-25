package com.ann.function.thread.exchanger;

import com.ann.utils.Utils;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class Bike extends Thread {
    private Exchanger<String> exchanger;

    public Bike(Exchanger<String> exchanger) {
        super();
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5000));
            Utils.msg(Thread.currentThread().getName() + ":Bike 到达交换地点，自己的数据为 Bike");
            Utils.msg(Thread.currentThread().getName() + ":Bike 获取交换数据 = " + exchanger.exchange("Bike"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
