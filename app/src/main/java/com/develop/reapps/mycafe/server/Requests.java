package com.develop.reapps.mycafe.server;

import android.content.Context;
import android.widget.Toast;

public class Requests {
    public final static String baseUrl = "http://62.109.23.83";


    public static void makeToastNotification(Context context, Integer status) {
        if (status == null) {
            status = -1;
        }
        String s = status == 200 ? "OK" : (status < 500 ? "Что -то пошло не так" : "Что-то на сервере пошлоне так");
        Toast.makeText(context, s + "\n" + status, Toast.LENGTH_SHORT).show();
    }

    public static void makeToastNotification(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
