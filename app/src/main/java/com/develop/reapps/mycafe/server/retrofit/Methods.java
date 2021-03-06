package com.develop.reapps.mycafe.server.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public class Methods {
    public static final String APP_COOKIE = "MY_Cookie";

    public static HashSet<String> getCookies(Context context) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(APP_COOKIE, Context.MODE_PRIVATE);
        return (HashSet<String>) mcpPreferences.getStringSet("cookies", new HashSet<String>());
    }

    public static boolean setCookies(Context context, HashSet<String> cookies) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(APP_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putStringSet("cookies", cookies).commit();
    }
}
