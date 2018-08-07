package com.ann.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ann.BaseActivity;
import com.ann.R;
import com.ann.constant.Constant;
import com.ann.designpattern.create.factory.simple.FragmentFactory;

/**
 * 功能区
 * <p>
 * Author:maxiaolong
 * Date:2018/7/13
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class FunctionActivity extends BaseActivity {


    private FrameLayout mFragmentContainer;
    private ImageView mBackIV;
    private String mTitle;
    private TextView mEmptyTV;
    private TextView mTitleTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(Constant.EXTRA_ACTIVITY_TITLE);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mFragmentContainer = findViewById(R.id.fl_container);
        mBackIV = findViewById(R.id.ic_left);
        mBackIV.setVisibility(View.VISIBLE);
        mBackIV.setOnClickListener(this::back);
        mEmptyTV = findViewById(R.id.tv_empty);
        mTitleTV = findViewById(R.id.tv_title);
        mTitleTV.setText(mTitle);

        Fragment fragment = FragmentFactory.createFragment(mTitle);

        if (TextUtils.isEmpty(mTitle) || fragment == null) {
            mEmptyTV.setVisibility(View.VISIBLE);
            mFragmentContainer.setVisibility(View.GONE);
        } else {
            getSupportFragmentManager().
                    beginTransaction().
                    add(mFragmentContainer.getId(), fragment).
                    commitNowAllowingStateLoss();

            mEmptyTV.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 左上角返回按钮点击事件
     *
     * @param view
     */
    private void back(View view) {
        finish();
    }


    /**
     * 启动FunctionActivity类
     */
    public static void startActivity(Context context, String title) {
        Intent intent = new Intent(context, FunctionActivity.class);
        intent.putExtra(Constant.EXTRA_ACTIVITY_TITLE, title);
        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
