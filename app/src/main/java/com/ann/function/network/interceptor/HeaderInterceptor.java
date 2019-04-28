package com.ann.function.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //在这里你可以做一些想做的事,比如token失效时,重新获取token
        //或者添加header等等,PS我会在下一篇文章总写拦截token方法


        return chain.proceed(request);

    }
}
