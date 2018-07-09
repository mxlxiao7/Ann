package com.ann.home;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ann.BaseFragment;
import com.ann.R;

@SuppressLint("InflateParams")
public class PlatformFragment extends BaseFragment {

    public static PlatformFragment newInstance() {
        PlatformFragment fragmentOne = new PlatformFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", fragmentOne.getClass().getSimpleName());
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_platform, null);
        return view;

    }

    public void initView() {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
