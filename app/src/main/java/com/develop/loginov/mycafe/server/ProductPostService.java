package com.develop.loginov.mycafe.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface ProductPostService {
    @POST("/menu")
    Call<String> loadProduct(@Field("name") String name,
                             @Field("description") String description,
                             @Field("price") int price,
                             @Field("weight") int weight,
                             @Field("type") int type,
                             @Field("image") byte[] image
    );
}
