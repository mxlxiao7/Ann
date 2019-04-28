package com.ann.designpattern.mvp;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * @author maxiaolong3
 * @version V1.0
 * @description:
 * @date 2018/11/3 下午4:20
 */
public interface IBaseView<P extends IBasePresenter> {

    /**
     * 获取环境上下文
     *
     * @return Context
     */
    @NotNull
    Context getContext();

}
