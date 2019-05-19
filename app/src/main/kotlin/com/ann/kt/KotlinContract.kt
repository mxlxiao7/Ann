package com.ann.kt

import com.ann.designpattern.mvp.IBasePresenter
import com.ann.designpattern.mvp.IBaseView

/**
 * @author leon
 * @date 2019-05-15 23:40
 */
class KotlinContract {

     interface IKotlinView : IBaseView<IKotlinPresenter>


     interface IKotlinPresenter : IBasePresenter<IKotlinView> {

        fun method1()

        fun method2(name: String, pwd: String)
    }

}
