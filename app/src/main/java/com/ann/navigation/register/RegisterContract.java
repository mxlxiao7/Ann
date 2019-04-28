package com.ann.navigation.register;

import com.ann.designpattern.mvp.IBasePresenter;
import com.ann.designpattern.mvp.IBaseView;

/**
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class RegisterContract {

    interface IRegisterView extends IBaseView<IRegisterPresenter> {


    }

    interface IRegisterPresenter extends IBasePresenter<IRegisterView> {

        void register(String name, String pwd);
    }

}