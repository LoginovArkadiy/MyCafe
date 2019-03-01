package com.develop.reapps.mycafe.server.retrofit;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests {
    public final static String baseUrl = "http://185.43.6.57/";


    public static void makeToastNotification(Context context, Integer status) {

        String s = status == null || status != 200 ? (status != null && status < 500 ? "Здесь что-то пошло не так" : "Там что-то пошло не так") : "OK";
        Toast.makeText(context, s + "\n" + status, Toast.LENGTH_SHORT).show();
    }

    public static void makeToastNotification(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().connectTimeout(60 * 5, TimeUnit.SECONDS).readTimeout(60 * 5, TimeUnit.SECONDS).writeTimeout(60 * 5, TimeUnit.SECONDS);
            okHttpClient.interceptors().add(new AddCookiesInterceptor());
            okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient.build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
