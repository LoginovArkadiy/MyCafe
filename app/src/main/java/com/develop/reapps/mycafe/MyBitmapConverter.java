package com.develop.reapps.mycafe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Leonid on 08.08.2018.
 */

public class MyBitmapConverter {

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public static Bitmap getBitmapfromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }

    public static Bitmap resizeBItmap(Bitmap bitmap, int k) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / k, bitmap.getHeight() / k, true);
    }
}
