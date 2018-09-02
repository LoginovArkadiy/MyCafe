package com.develop.loginov.mycafe.order.basket;

import com.develop.loginov.mycafe.Product;

import java.util.HashMap;

public class MyBasket {

    private HashMap<Product, Integer> basket;

    public MyBasket(HashMap<Product, Integer> basket) {
        this.basket = basket;
    }

    public HashMap<Product, Integer> getBasket() {
        return basket;
    }
}
