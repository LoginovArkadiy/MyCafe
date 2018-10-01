package com.develop.reapps.mycafe;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyImage {
    private ImageView imageView;
    private Bitmap bitmap;

    public MyImage(ImageView imageView, Bitmap bitmap) {
        this.imageView = imageView;
        this.bitmap = bitmap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
