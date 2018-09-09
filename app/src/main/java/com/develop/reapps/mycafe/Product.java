package com.develop.reapps.mycafe;

import android.graphics.Bitmap;

public class Product {
    private static final int LENGTH_DESCRIPTION = 40;

    private String name, description, edition;
    private int id, price, weight, type;
    private int count;
    private byte[] bytes;
    private Bitmap bitmap;


    public Product(String name, String description, int id, int price, int weight) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.price = price;
        this.weight = weight;
        count = 0;
        edition = "";
        type = 0;
    }

    public Product(String name, String description, int price, int weight, int type, Bitmap bitmap) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.type = type;
        this.bitmap = bitmap;
    }

    public int getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + edition.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Product p2 = (Product) obj;
        return name.equals(p2.getName()) && edition.equals(p2.getEdition());
    }

    public String getEdition() {
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


    public int getId() {
        return id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public byte[] getBytes() {
        if (bytes == null) bytes = MyBitmapConverter.getByteArrayFromBitmap(bitmap);
        return bytes;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) bitmap = MyBitmapConverter.getBitmapfromByteArray(bytes);

        return bitmap;
    }
}
