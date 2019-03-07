package com.develop.reapps.mycafe.server.retrofit;

import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.MainActivity;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = Methods.getCookies(MainActivity.getAppContext());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
