package com.ann.other;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ann.BaseFragment;
import com.ann.R;
import com.orhanobut.logger.Logger;

public class OtherFragment extends BaseFragment {

    public static OtherFragment newInstance() {
        OtherFragment fragmentOne = new OtherFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        if (getArguments() != null) {
            //取出保存的值
            textView.setText(getArguments().getString("name"));
        }
        return view;

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
