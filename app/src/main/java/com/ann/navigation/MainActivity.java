package com.ann.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ann.BaseActivity;
import com.ann.R;
import com.ann.widget.BottomNavigationViewHelper;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

/**
 * 页面基类
 * 承载4个页面
 * <p>
 * Author:maxiaolong
 * Date:2018/7/24
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView mNavigation;
    private FrameLayout mFragmentContainer;
    private int releaseIndex = -1;

    /**
     * 切换tab数据，初始化时就赋值
     */
    private LinkedHashMap<Integer, TabEntry> mTab = new LinkedHashMap() {{
        put(0, new TabEntry(null, HomeFragment.class));
        put(1, new TabEntry(null, AlgorithmsFragment.class));
        put(2, new TabEntry(null, PlatformFragment.class));
        put(3, new TabEntry(null, MainFragment.class));
    }};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void init() {
        mFragmentContainer = findViewById(R.id.ll_container);
        mNavigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setSelectedItemId(R.id.navigation_home);

        runOnUiThread(() -> mNavigation.setSelectedItemId(R.id.navigation_home));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int index = -1;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                index = 0;
                break;
            case R.id.navigation_algorithms:
                index = 1;
                break;
            case R.id.navigation_platform:
                index = 2;
                break;
            case R.id.navigation_main:
                index = 3;
                break;
        }

        if (index >= 0 && index != releaseIndex) {
            switchTab(index, releaseIndex);
            releaseIndex = index;
            return true;
        }

        return false;
    }


    /**
     * 切换tab
     *
     * @param selectIndex
     * @param releaseIndex
     */
    private void switchTab(int selectIndex, int releaseIndex) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //隐藏当前页面
        for (int i = 0; i < mTab.size(); i++) {
            Fragment fragment = mTab.get(i).fragment;
            if (fragment == null || selectIndex == i || fragment.isHidden()) {
                continue;
            }
            transaction.hide(fragment);
        }

        //获取切换目标页面
        Fragment selectFragment = getFragmentWithIndex(selectIndex);
        do {
            if (selectFragment == null) {
                throw new IllegalArgumentException("Select Fragment is null");
            }

            if (!selectFragment.isAdded()) {
                transaction.add(mFragmentContainer.getId(), selectFragment);
            }

            if (selectFragment.isHidden()) {
                transaction.show(selectFragment);
            }
        } while (false);

        transaction.commitAllowingStateLoss();
    }


    /**
     * 获取Fragment
     *
     * @param index 目标索引，从0开始
     * @return
     */
    private Fragment getFragmentWithIndex(int index) {

        if (index < 0 || index > 3) {
            return null;
        }

        final TabEntry tabEntry = mTab.get(index);
        Fragment fragment = tabEntry.fragment;
        if (fragment == null) {
            try {
                Method method = tabEntry.cls.getDeclaredMethod("newInstance");
                Object invoke = method.invoke(null);
                fragment = (Fragment) invoke;
            } catch (Exception e) {
                Logger.e(Log.getStackTraceString(e));
            }
            tabEntry.fragment = fragment;
        }

        return fragment;
    }
}
