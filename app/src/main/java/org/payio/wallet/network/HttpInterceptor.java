package org.payio.wallet.network;


import org.payio.wallet.utils.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {
    private final static String TAG = "REQUEST_URL == ";

    @Override
    public Response intercept(Chain chain) throws IOException {

        String url = chain.request().url().toString();

        Log.i(TAG + url);

        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();

//                .addHeader("Cookie", SharedPreference.get(OTCApplication.APPLICATION).getStringValue(User.USER_COOKIE))


        /*Response response = chain.proceed(request);

        String s = response.body().string();

        Log.i(TAG + "---原始报文", s);*/

        return chain.proceed(request);
    }
}