package com.ann.function.network;

import com.ann.function.network.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ann.function.network.ApiServer.BASE_URL;


/**
 * RetrofitFactory
 * <p>
 * Author:maxiaolong
 * Date:2018/7/17
 * Time:10:43
 * Email:mxlxiao7@sina.com
 */
public class RetrofitFactory {


    public static final int HTTP_TIME = 5;


    /**
     * 公共网络Retrofit
     */
    private static volatile Retrofit mNormalRetrofit;

    /**
     * api
     */
    private static volatile ApiServer mApiServer;


    public static Retrofit getNoraml() {
        if (mNormalRetrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (mNormalRetrofit == null) {

                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(HTTP_TIME, TimeUnit.SECONDS)
                            .readTimeout(HTTP_TIME, TimeUnit.SECONDS)
                            .writeTimeout(HTTP_TIME, TimeUnit.SECONDS)
                            .addInterceptor(new HeaderInterceptor())
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//添加日志拦截器
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                            .client(okHttpClient)
                            .build();

                    mNormalRetrofit = retrofit;
                }
            }

        }
        return mNormalRetrofit;
    }

    /**
     * @return
     */
    public static ApiServer API() {
        if (mApiServer == null) {
            mApiServer = getNoraml().create(ApiServer.class);
        }
        return mApiServer;
    }
}
