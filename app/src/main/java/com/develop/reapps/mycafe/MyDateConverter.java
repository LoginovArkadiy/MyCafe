package com.develop.reapps.mycafe;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyDateConverter {

    public static String getMoment() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }
}
