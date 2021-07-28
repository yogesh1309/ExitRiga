package com.cw.exitriga.Server;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("app_key", "exitriga#2021");
        builder.addHeader("app_secret", "EXIT21RIGA#");

        return chain.proceed(builder.build());
    }
}