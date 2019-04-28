package com.ann;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ann.designpattern.mvp.IBasePresenter;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import icepick.Icepick;

/**
 * Activity基类
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity {

    /**
     * 业务控制器
     */
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, getColor(R.color.colorPrimary));
        StatusBarUtil.setDarkMode(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mPresenter = createPresenter();
        if (Objects.nonNull(mPresenter)) {
            getLifecycle().addObserver(mPresenter);
        }

        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Objects.nonNull(mPresenter)) {
            getLifecycle().removeObserver(mPresenter);
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        AnnApplication.getRefWatcher(this).watch(this);
    }


    /**
     * 钩子方法，让实现MVP的Activity实现
     *
     * @return
     */
    protected P createPresenter() {
        //Sub Class hook ,here do nothing
        return null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }


    /**
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void emptyEvent(String message) {
        //Sub Class hook ,here do nothing
    }


}
