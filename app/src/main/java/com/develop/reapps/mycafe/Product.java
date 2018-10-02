package com.develop.reapps.mycafe;

import android.graphics.Bitmap;

import com.develop.reapps.mycafe.server.uploads.UploadClient;
import com.develop.reapps.mycafe.server.uploads.UploadsService;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Product {
    private static final int LENGTH_DESCRIPTION = 40;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("price")
    private int price;
    @SerializedName("weight")
    private int weight;
    @SerializedName("strType")
    private String strType;
    @SerializedName("imageId")
    private int imageId;
    @SerializedName("type")
    String menuSection;

    private Integer myDrawableId;


    private File file;
    private String edition;
    private int count;
    private Bitmap bitmap;


    public Product(String name, String description, int myDrawableId, int price, int weight) {
        this.name = name;
        this.description = description;
        this.myDrawableId = myDrawableId;
        this.price = price;
        this.weight = weight;
        count = 0;
        edition = "";
    }

    public Product(String name, String description, int price, int weight, String strType, File file) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.strType = strType;
        this.file = file;
        edition = "";
    }


    @Override
    public int hashCode() {
        if (edition == null) edition = "";
        return name.hashCode() + edition.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        Product p2 = (Product) obj;
        return name.equals(p2.getName()) && edition.equals(p2.getEdition());
    }

    public String getEdition() {
        if (edition == null) edition = "";
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return description.length() > LENGTH_DESCRIPTION + 3 ? description.substring(0, LENGTH_DESCRIPTION) + "..." : description;
    }

    public String getStrType() {
        return strType;
    }

    public int getMyDrawableId() {
        if (myDrawableId == null) myDrawableId = R.drawable.reapps;
        return myDrawableId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }


    public Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = new UploadClient().getImageById(imageId);
        }
        return bitmap;
    }



    public void setImageBitmap(android.widget.ImageView imageView) {
        new UploadClient().setImageBitmap(imageView, imageId);
    }

    public File getFile() {
        return file;
    }


}
