package com.develop.loginov.mycafe.server;

import com.develop.loginov.mycafe.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by Leonid on 07.08.2018.
 */

public interface OrderPostService {
    @POST("/order")
    Call<String> loadProduct(@Field("login") String login,
                             @Field("description") String description,
                             @Field("price") int price,
                             @Field("products") Product[] products
    );
}
