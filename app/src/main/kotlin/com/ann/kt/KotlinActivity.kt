package com.ann.kt

import android.os.Bundle
import com.ann.BaseActivity
import com.ann.R

/**
 *
 */
class KotlinActivity : BaseActivity<KotlinPresenter>() {

    var a:String = "bb";

    val b:String = "ab";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     *
     */
    private fun init() {

    }
}