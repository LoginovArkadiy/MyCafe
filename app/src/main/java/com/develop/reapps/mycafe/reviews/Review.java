
package com.develop.reapps.mycafe.reviews;


public class Review {
   private String text, date, login;

    public Review(String text, String date, String login) {
        this.text = text;
        this.date = date;
        this.login = login;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getLogin() {
        return login;
    }
}
