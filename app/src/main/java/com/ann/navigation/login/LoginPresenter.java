package com.ann.navigation.login;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Looper;
import android.widget.Toast;

import com.ann.designpattern.mvp.BasePresenter;
import com.ann.function.network.RetrofitFactory;
import com.ann.function.network.bean.HttpEntity;
import com.ann.function.network.bean.LoginInfo;
import com.ann.function.network.param.Login;
import com.orhanobut.logger.Logger;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {


    public LoginPresenter(LoginContract.ILoginView view) {
        super(view);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        super.onCreate(owner);
    }

    @Override
    public void loadData() {
        Toast.makeText(getContext(), "LoginPresenter", Toast.LENGTH_LONG).show();
    }

    @Override
    public void login(String name, String pwd) {
        RetrofitFactory.API()
                .login(new Login(name, pwd))
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<HttpEntity<LoginInfo>>() {
                    @Override
                    public void accept(HttpEntity<LoginInfo> loginInfoHttpEntity) throws Exception {
                        Logger.e(loginInfoHttpEntity.toString());
                        boolean flag = Looper.getMainLooper() == Looper.myLooper();

//                        loginInfoHttpEntity.data.userInfo.name = "111";
                        Logger.e("doOnNext1()" + flag);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<HttpEntity<LoginInfo>>() {
                    @Override
                    public void accept(HttpEntity<LoginInfo> loginInfoHttpEntity) throws Exception {
                        boolean flag = Looper.getMainLooper() == Looper.myLooper();

                        Logger.e("doOnNext2()" + flag);
                    }
                })
                .doAfterNext(new Consumer<HttpEntity<LoginInfo>>() {
                    @Override
                    public void accept(HttpEntity<LoginInfo> loginInfoHttpEntity) throws Exception {
                        boolean flag = Looper.getMainLooper() == Looper.myLooper();

                        Logger.e("doAfterNext()" + flag);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        boolean flag = Looper.getMainLooper() == Looper.myLooper();

                        Logger.e("doFinally()" + flag);

                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        boolean flag = Looper.getMainLooper() == Looper.myLooper();

                        Logger.e("doOnComplete()" + flag);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginInfoHttpEntity -> {
                            boolean flag = Looper.getMainLooper() == Looper.myLooper();
                            Logger.e("loginInfoHttpEntity()" + flag);
//                            Logger.e(loginInfoHttpEntity.toString());
                        }, throwable -> {
                            Logger.e("throwable()");
                        });
    }
}
