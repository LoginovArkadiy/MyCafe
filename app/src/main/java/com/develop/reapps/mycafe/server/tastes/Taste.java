package com.develop.reapps.mycafe.server.tastes;

import com.google.gson.annotations.SerializedName;

public class Taste {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("tobaccoId")
    private int tobaccoId;

    public Taste(int id, String name, int tobaccoId) {
        this.id = id;
        this.name = name;
        this.tobaccoId = tobaccoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTobaccoId() {
        return tobaccoId;
    }

    public void setTobaccoId(int tobaccoId) {
        this.tobaccoId = tobaccoId;
    }
}
