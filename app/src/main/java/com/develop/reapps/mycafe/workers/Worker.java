package com.develop.reapps.mycafe.workers;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.uploads.UploadClient;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserClient;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Worker {
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("post")
    private String post;
    @SerializedName("isWorkToday")
    private boolean isWorkToday;
    @SerializedName("imageId")
    private int imageId;

    private String name;

    private String email;

    User user;
    private Integer drawableId;
    private int role;
    private File file;
    Bitmap bitmap;


    public int getRole() {
        return role;
    }

    public Worker(String name, String post, String email, int role, File file) {
        this.name = name;
        this.post = post;
        this.email = email;
        this.role = role;
        this.file = file;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = new UploadClient().getImageById(imageId);
        }
        return bitmap;
    }

    public void setWorkToday(boolean workToday) {
        isWorkToday = workToday;
    }

    public String getEmail() {
        if (email != null) return email;
        if (user == null) {
            user = new UserClient().getUserById(userId);
        }
        return user.getEmail();
    }

    public String getName() {
        if (name != null) return name;
        if (user == null) {
            user = new UserClient().getUserById(userId);
        }
        return user.getLogin();
    }

    public String getPost() {
        return post;
    }

    public int getId_drawable() {
        if (drawableId == null) drawableId = R.drawable.reapps;
        return drawableId;
    }

    public boolean isWorkToday() {
        return isWorkToday;
    }

    public int getId() {
        return id;
    }

    public boolean changeWorkNow() {
        isWorkToday = !isWorkToday;
        return isWorkToday;
    }

    public File getFile() {
        return file;
    }

    public void setImageBitmap(ImageView imageView) {
        new UploadClient().setImageBitmap(imageView, imageId);
    }
}
