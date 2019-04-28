package com.ann.designpattern.mvp;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author maxiaolong3
 * @version V1.0
 * @description:
 * @date 2018/11/3 下午4:20
 */
public abstract class BasePresenter<V extends IBaseView> extends ViewModel implements IBasePresenter<V> {

    protected V mView;

    private CompositeDisposable mCompositeDisposable; //防止RxJava 内存泄漏


    protected BasePresenter(V view) {
        this.mView = view;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onStart(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onAny(LifecycleOwner owner) {

    }

    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onStop(LifecycleOwner owner) {

    }

    @Override
    public void onDestory(LifecycleOwner owner) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    /**
     * 添加
     */
    public void addDisposable(CompositeDisposable c) {
        mCompositeDisposable.add(c);
    }


    /**
     * get context instance
     *
     * @return
     */
    protected Context getContext() {
        return mView.getContext();
    }


}
