package com.ann.navigation.login;

import com.ann.designpattern.mvp.IBasePresenter;
import com.ann.designpattern.mvp.IBaseView;

/**
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class LoginContract {

    interface ILoginView extends IBaseView<ILoginPresenter> {


    }

    interface ILoginPresenter extends IBasePresenter<ILoginView> {

        void loadData();

        void login(String name, String pwd);
    }

}