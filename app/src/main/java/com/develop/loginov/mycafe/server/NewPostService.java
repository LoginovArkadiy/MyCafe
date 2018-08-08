package com.develop.loginov.mycafe.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by Leonid on 07.08.2018.
 */

public interface NewPostService {
    @POST("/menu")
    Call<String> loadNew(@Field("title") String title,
                             @Field("description") String description,
                             @Field("image") int[] image,
                             @Field("type") int type);
}
