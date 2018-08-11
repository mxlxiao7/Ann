package com.ann.function.io;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ann.BaseFragment;
import com.ann.R;
import com.ann.designpattern.create.factory.simple.FragmentFactory;
import com.ann.function.FunctionActivity;

/**
 * IO
 * <p>
 * Author:maxiaolong
 * Date:2018/8/02
 * Email:mxlxiao7@sina.com
 */
public class IOFragment extends BaseFragment {

    private static final String TAG = IOFragment.class.getSimpleName();
    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;

    public IOFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static IOFragment newInstance() {
        IOFragment fragment = new IOFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_io_layout, container, false);

        mBtn0 = rootView.findViewById(R.id.btn0);
        mBtn0.setOnClickListener(view -> start(FragmentFactory.SUB_TITLES[0]));

        mBtn1 = rootView.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(view -> start(FragmentFactory.SUB_TITLES[1]));

        mBtn2 = rootView.findViewById(R.id.btn2);
        mBtn2.setOnClickListener(view -> start(FragmentFactory.SUB_TITLES[2]));

        mBtn3 = rootView.findViewById(R.id.btn3);
        mBtn3.setOnClickListener(view -> start(FragmentFactory.SUB_TITLES[3]));
        return rootView;
    }

    /**
     * 启动子页面
     */
    private void start(String item) {
        FunctionActivity.startActivity(getContext(), item);
    }
}