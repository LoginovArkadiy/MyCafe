package com.develop.reapps.mycafe.order;

import com.develop.reapps.mycafe.Product;

import java.util.HashMap;

public class Order {
    StringBuilder beatyOrder;
    private HashMap<Product, Integer> hm;
    private Integer numberTable;
    private String beginTime, endTime;

    public Order(HashMap<Product, Integer> hm, int numberTable, String beginTime, String endTime) {
        this.hm = hm;
        this.numberTable = numberTable;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Order(HashMap<Product, Integer> hm) {
        this.hm = hm;
    }

    public Order(String s) {
        parseOrder(s);
    }


    public Order() {

    }

    public void setHashMap(HashMap<Product, Integer> hm) {
        this.hm = hm;
    }

    public void setNumberTable(int numberTable) {
        this.numberTable = numberTable;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrder() {
        StringBuilder s = new StringBuilder();
        for (Product p : hm.keySet()) {
            s.append(p.getName()).append(" ").append(hm.get(p)).append("\n").append(p.getEdition()).append("\n");
        }

        s.append("Номер столика - ").append(numberTable).append("\n");
        s.append("Начало брони - ").append(beginTime).append("\n");
        s.append("Конец брони - ").append(endTime).append("\n");
        setBeatyOrder(s.toString());
        return s.toString();
    }

    public String getBeatyOrder() {
        return beatyOrder.toString();
    }

    public void setBeatyOrder(String s) {
        char[] a = s.toCharArray();
        String s2 = "";
        for (int i = 0; i < a.length; i++) {
            if (a[i] != ' ') {
                s2 += a[i];
            } else {

            }
        }
    }

    private void parseOrder(String s) {
    }

    public boolean isGood() {
        return !(hm == null || numberTable == null || beginTime == null || endTime == null);
    }
}
