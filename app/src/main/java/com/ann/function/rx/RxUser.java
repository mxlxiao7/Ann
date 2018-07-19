package com.ann.function.rx;

/**
 *
 */
public class RxUser {


    public String name = "Root";

    public RxCar car = new RxCar();


    @Override
    public String toString() {
        return "RxUser{" +
                "name='" + name + '\'' +
                ", car=" + car.toString() +
                '}';
    }
}
