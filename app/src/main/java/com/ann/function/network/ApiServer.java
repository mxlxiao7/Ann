package com.ann.function.network;


import com.ann.function.network.bean.HttpEntity;
import com.ann.function.network.bean.LoginInfo;
import com.ann.function.network.param.Login;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * RetrofitFactory
 * <p>
 * Author:maxiaolong
 * Date:2018/7/17
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public interface ApiServer {


    String BASE_URL = "http://192.168.31.106:8080/";


    /**
     * 用户登录接口
     *
     * @param data
     * @return
     */
    @POST("user/login")
    Flowable<HttpEntity<LoginInfo>> login(@Body Login data);


    /**
     * 用户注册接口
     *
     * @param data
     * @return
     */
    @POST("user/register")
    Flowable<HttpEntity<LoginInfo>> register(@Body Login data);
}
