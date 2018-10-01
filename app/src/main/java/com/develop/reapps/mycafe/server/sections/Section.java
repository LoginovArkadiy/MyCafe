package com.develop.reapps.mycafe.server.sections;

import com.google.gson.annotations.SerializedName;

public class Section {
    @SerializedName("type")
    private String section;
    @SerializedName("id")
    private int id;

    public String getSection() {
        return section;
    }

    public int getId() {
        return id;
    }
}
