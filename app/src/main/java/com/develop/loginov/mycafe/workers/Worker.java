package com.develop.loginov.mycafe.workers;

public class Worker {
    private String name, post, email;
    private int id_drawable;
    private boolean isWorkNow;

    public Worker(String name, String post, int id_drawable, boolean isWorkNow) {
        this.name = name;
        this.post = post;
        this.id_drawable = id_drawable;
        this.isWorkNow = isWorkNow;

    }

    public Worker(String name, String post, String email, int id_drawable, boolean isWorkNow) {
        this.name = name;
        this.post = post;
        this.email = email;
        this.id_drawable = id_drawable;
        this.isWorkNow = isWorkNow;
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

    public boolean changeWorkNow() {
        isWorkNow = !isWorkNow;
        return isWorkNow;
    }
}
