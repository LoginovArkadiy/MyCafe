package com.develop.loginov.mycafe.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface WorkerPostService {
    @POST("/worker")
    Call<String> loadNew(@Field("id") int id,
                         @Field("role") int role,
                         @Field("post") String post,
                         @Field("image") byte[] image
    );

}
