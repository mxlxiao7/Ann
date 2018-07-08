package com.ann.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ann.BaseActivity;
import com.ann.R;
import com.ann.algorithm.AlgorithmsFragment;
import com.ann.utils.BottomNavigationViewHelper;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView mNavigation;
    private FrameLayout mFragmentContainer;
    private Fragment[] mFragments = new Fragment[5];
    private int releaseIndex = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mFragmentContainer = (FrameLayout) findViewById(R.id.ll_container);
        mNavigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.setSelectedItemId(R.id.navigation_home);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNavigation.setSelectedItemId(R.id.navigation_main);
            }
        });

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
            case R.id.navigation_other:
                index = 3;
                break;
            case R.id.navigation_main:
                index = 4;
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
     * @param selectIndex
     * @param releaseIndex
     */
    private void switchTab(int selectIndex, int releaseIndex) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment releaseFragment = getFragmentWithIndex(releaseIndex);
        if (releaseFragment != null) {
            transaction.hide(releaseFragment);
        }

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
     */
    private Fragment getFragmentWithIndex(int index) {

        if (index < 0 || index > 4) {
            return null;
        }

        Fragment fragment = mFragments[index];
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = HomeFragment.newInstance();
                    break;
                case 1:
                    fragment = AlgorithmsFragment.newInstance();
                    break;
                case 2:
                    fragment = PlatformFragment.newInstance();
                    break;
                case 3:
                    fragment = OtherFragment.newInstance();
                    break;
                case 4:
                    fragment = MainFragment.newInstance();
                    break;
            }
            mFragments[index] = fragment;
        }

        return fragment;
    }


}
