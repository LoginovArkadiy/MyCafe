package com.develop.loginov.mycafe.workers;

public class Worker {
    private String name, workname;
    private int id_drawable;
    private boolean isWorkNow;

    public Worker(String name, String workname, int id_drawable, boolean isWorkNow) {
        this.name = name;
        this.workname = workname;
        this.id_drawable = id_drawable;
        this.isWorkNow = isWorkNow;
    }

    public String getName() {
        return name;
    }

    public String getWorkname() {
        return workname;
    }

    public int getId_drawable() {
        return id_drawable;
    }

    public boolean isWorkNow() {
        return isWorkNow;
    }
}
