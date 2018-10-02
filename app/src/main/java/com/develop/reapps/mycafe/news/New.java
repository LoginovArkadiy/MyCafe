package com.develop.reapps.mycafe.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.uploads.UploadClient;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class New {
    @SerializedName("header")
    private String name;
    @SerializedName("text")
    private String description;
    @SerializedName("imageId")
    private int imageId;
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private String time;

    // private ImageView imageView;

    File file;
    private Integer drawableId;
    private Bitmap bitmap;


    public New(String name, String description, String time,  File file) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.file = file;
    }



    public Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = new UploadClient().getImageById(imageId);
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawableId() {
        if (drawableId == null) drawableId = R.drawable.reapps;
        return drawableId;
    }

    public String getTime() {
        return time;
    }

    public void setImageBitmap(ImageView imageView) {
        new UploadClient().setImageBitmap(imageView, imageId);
    }
}
