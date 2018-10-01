package com.develop.reapps.mycafe.server.retrofit;

import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.MainActivity;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Methods.setCookies(MainActivity.getAppContext(), cookies);
        }
        return originalResponse;
    }
}
