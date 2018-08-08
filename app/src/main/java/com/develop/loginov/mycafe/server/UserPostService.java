package com.develop.loginov.mycafe.server;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface UserPostService {
   // @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/signup")
    Call<String> loadUser(@Field("login") String login, @Field("password") String password, @Field("email") String email);
    //Call<String> loadUser(@Body UserRegistrationBody user);
}
