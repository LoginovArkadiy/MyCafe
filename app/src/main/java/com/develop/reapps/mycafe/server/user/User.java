package com.develop.reapps.mycafe.server.user;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    public String login;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;
    @SerializedName("id")
    public int id;
    @SerializedName("role")
    public int role;
    @SerializedName("imageId")
    public int imageId;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
