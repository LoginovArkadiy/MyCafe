package com.develop.reapps.mycafe.reviews;


import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("text")
    private String text;
    @SerializedName("date")
    private String date;
    @SerializedName("authorId")
    private int authorId;

    String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Review(String text, String date, int authorId) {
        this.text = text;
        this.date = date;
        this.authorId = authorId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
