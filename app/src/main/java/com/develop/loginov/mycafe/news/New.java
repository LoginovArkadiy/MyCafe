package com.develop.loginov.mycafe.news;

import android.widget.SeekBar;

public class New {
    private String name, description, time;
    private int drawableId;

    public New(String name, String description, int drawableId, String time) {
        this.name = name;
        this.time = time;
        this.description = description;
        this.drawableId = drawableId;
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
