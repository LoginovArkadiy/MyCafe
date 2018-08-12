package com.develop.loginov.mycafe.news;

import android.graphics.Bitmap;
import android.widget.SeekBar;

import com.develop.loginov.mycafe.MyBitmapConverter;

public class New {
    private String name, description, time;
    private int drawableId, id;
    private byte[] bytes;


    private Bitmap bitmap;

    public New(String name, String description, int drawableId, String time) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.drawableId = drawableId;
    }

    public New(String name, String description, String time, Bitmap bitmap) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) bitmap = MyBitmapConverter.getBitmapfromByteArray(bytes);
        return bitmap;
    }

    public byte[] getBytes() {
        if (bytes == null) bytes = MyBitmapConverter.getByteArrayFromBitmap(bitmap);
        return bytes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getTime() {
        return time;
    }
}
