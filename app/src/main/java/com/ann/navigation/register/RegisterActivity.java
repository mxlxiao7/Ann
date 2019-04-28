package com.ann.navigation.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ann.BaseActivity;
import com.ann.R;
import com.ann.navigation.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

/**
 * 个人注册页面
 * <p>
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class RegisterActivity extends BaseActivity<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView, View.OnClickListener {


    /**
     *
     */
    private ImageView mBackIV;
    private TextView mRegisterTV;
    private EditText mNameET;
    private EditText mPwdET;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        init();
    }

    @NonNull
    @Override
    protected RegisterContract.IRegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    /**
     * 初始化
     */
    private void init() {
        mBackIV = findViewById(R.id.iv_back);
        mBackIV.setOnClickListener(this);

        mRegisterTV = findViewById(R.id.tv_register);
        mRegisterTV.setOnClickListener(this);

        mNameET = findViewById(R.id.et_name);
        mPwdET = findViewById(R.id.et_pwd);
    }

    @NotNull
    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == mBackIV.getId()) {
            finish();
        } else if (v.getId() == mRegisterTV.getId()) {
            //获取账户和密码
            String name = mNameET.getText().toString();
            String pwd = mPwdET.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
                mPresenter.register(name, pwd);
            } else {
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     *
     */
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }


}
