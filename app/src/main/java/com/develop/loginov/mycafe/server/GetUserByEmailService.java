package com.develop.loginov.mycafe.server;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetUserByEmailService {
    @GET("/users")
    Call<User> getUser(@Query("email") String email);
}
