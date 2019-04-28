package com.ann.navigation.register;


import com.ann.designpattern.mvp.BasePresenter;
import com.ann.function.network.RetrofitFactory;
import com.ann.function.network.bean.HttpEntity;
import com.ann.function.network.bean.LoginInfo;
import com.ann.function.network.param.Login;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册页面业务逻辑控制器
 * <p>
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter {


    public RegisterPresenter(RegisterContract.IRegisterView view) {
        super(view);

    }

    @Override
    public void register(String name, String pwd) {
        RetrofitFactory.API()
                .register(new Login(name, pwd))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<HttpEntity<LoginInfo>>() {
                    @Override
                    public void accept(HttpEntity<LoginInfo> loginInfoHttpEntity) throws Exception {
                        Logger.e("doOnNext()");
                    }
                })
                .doAfterNext(new Consumer<HttpEntity<LoginInfo>>() {
                    @Override
                    public void accept(HttpEntity<LoginInfo> loginInfoHttpEntity) throws Exception {
                        Logger.e("doAfterNext()");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.e("doFinally()");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.e("doOnComplete()");
                    }
                })
                .subscribe(
                        loginInfoHttpEntity -> {
                            Logger.e("loginInfoHttpEntity()");
                            Logger.e(loginInfoHttpEntity.toString());

                        }, throwable -> {
                            Logger.e("throwable()");
                        });

    }
}
