package com.develop.loginov.mycafe.server.order;

import com.develop.loginov.mycafe.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface OrderService {
    @POST("/order")
    Call<String> loadProduct(@Field("login") String login,
                             @Field("description") String description,
                             @Field("price") int price,
                             @Field("products") Product[] products
    );
}
