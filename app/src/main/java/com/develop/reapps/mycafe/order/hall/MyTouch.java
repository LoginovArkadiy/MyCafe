package com.develop.reapps.mycafe.order.hall;


public class MyTouch {
    private int angle;
    private boolean isTable;
    private int index;

    public MyTouch(int angle, boolean isTable, int index) {
        this.angle = angle;
        this.isTable = isTable;
        this.index = index;
    }

    public MyTouch(int type, boolean isTable) {
        this.angle = type;
        this.isTable = isTable;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getAngle() {
        return angle;
    }

    public boolean isTable() {
        return isTable;
    }

    public int getIndex() {
        return index;
    }
}
