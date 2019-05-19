package com.ann.kt;

import android.arch.lifecycle.LifecycleOwner;

import com.ann.designpattern.mvp.BasePresenter;

import org.jetbrains.annotations.NotNull;


/**
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class KotlinPresenter extends BasePresenter<KotlinContract.IKotlinView> implements KotlinContract.IKotlinPresenter {


    public KotlinPresenter(KotlinContract.IKotlinView view) {
        super(view);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        super.onCreate(owner);
    }


    @Override
    public void method1() {

    }

    @Override
    public void method2(@NotNull String name, @NotNull String pwd) {

    }
}
