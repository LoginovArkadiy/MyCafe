package com.develop.loginov.mycafe.workers;

import android.graphics.Bitmap;

import com.develop.loginov.mycafe.MyBitmapConverter;

public class Worker {
    private String name, post, email;
    private int id_drawable, id, role;


    private byte[] bytes;
    Bitmap bitmap;


    private boolean isWorkNow;

    public Worker(String name, String post, int id_drawable, boolean isWorkNow) {
        this.name = name;
        this.post = post;
        this.id_drawable = id_drawable;
        this.isWorkNow = isWorkNow;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getRole() {
        return role;
    }

    public Worker(String name, String post, String email, int role, Bitmap bitmap) {
        this.name = name;
        this.post = post;
        this.email = email;
        this.role = role;
        this.bitmap = bitmap;
        bytes = MyBitmapConverter.getByteArrayFromBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap == null ? MyBitmapConverter.getBitmapfromByteArray(bytes) : bitmap;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public int getId_drawable() {
        return id_drawable;
    }

    public boolean isWorkNow() {
        return isWorkNow;
    }

    public int getId() {
        return id;
    }

    public boolean changeWorkNow() {
        isWorkNow = !isWorkNow;
        return isWorkNow;
    }
}
