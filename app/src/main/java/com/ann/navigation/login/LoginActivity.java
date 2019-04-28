package com.ann.navigation.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ann.BaseActivity;
import com.ann.R;
import com.ann.navigation.register.RegisterActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Author:maxiaolong
 * Date:2018/11/4
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class LoginActivity extends BaseActivity<LoginContract.ILoginPresenter> implements LoginContract.ILoginView, View.OnClickListener {

    /**
     *
     */
    private ImageView mCloseIV;
    private TextView mLoginTV;
    private EditText mNameET;
    private EditText mPwdET;
    private TextView mRegisterTV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy");
    }

    @NonNull
    @Override
    protected LoginContract.ILoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    /**
     * 初始化
     */
    private void init() {
        mCloseIV = findViewById(R.id.iv_close);
        mCloseIV.setOnClickListener(this);

        mLoginTV = findViewById(R.id.tv_login);
        mLoginTV.setOnClickListener(this);

        mNameET = findViewById(R.id.et_name);
        mPwdET = findViewById(R.id.et_pwd);

        mRegisterTV = findViewById(R.id.tv_register);
        mRegisterTV.setOnClickListener(this);

    }

    @NotNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mCloseIV.getId()) {
            finish();
        } else if (v.getId() == mLoginTV.getId()) {
            //获取账户和密码
            String name = mNameET.getText().toString();
            String pwd = mPwdET.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
                mPresenter.login(name, pwd);
            } else {
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == mRegisterTV.getId()) {
            //新用户注册
            RegisterActivity.openActivity(this);
        }
    }


    /**
     *
     */
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

}
