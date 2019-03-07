package com.develop.reapps.mycafe.menu.element;


import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Tobacco extends Product {
    @SerializedName("tastes")
    private String[] tastes;

    public Tobacco(String name, String description, int price, File file) {
        super(name, description, price, file);
    }


}
