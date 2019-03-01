package com.develop.reapps.mycafe.order.basket;

import com.develop.reapps.mycafe.menu.element.Product;

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
