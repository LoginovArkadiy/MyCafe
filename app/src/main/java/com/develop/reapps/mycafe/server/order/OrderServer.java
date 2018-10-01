package com.develop.reapps.mycafe.server.order;

public class OrderServer {
    int id, ownerId;
    boolean completed;
    String order;

    public OrderServer(int id, int ownerId, boolean completed, String order) {
        this.id = id;
        this.ownerId = ownerId;
        this.completed = completed;
        this.order = order;
    }
}
