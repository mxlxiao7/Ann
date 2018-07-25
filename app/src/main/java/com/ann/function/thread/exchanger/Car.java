package com.ann.function.thread.exchanger;


import com.ann.utils.Utils;

import java.util.Random;
import java.util.concurrent.Exchanger;


public class Car extends Thread {
    private Exchanger<String> exchanger;

    public Car(Exchanger<String> exchanger) {
        super();
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5000));
            Utils.msg(Thread.currentThread().getName() + ":Car 到达交换地点，自己的数据为 Car");
            Utils.msg(Thread.currentThread().getName() + ":Car 获取交换数据 = " + exchanger.exchange("Car"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}