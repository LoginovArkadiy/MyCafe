package com.develop.reapps.mycafe.server.user;

import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserService {
    @FormUrlEncoded
    @POST("/signup")
    Call<AnswerBody> loadUser(@Field("login") String login, @Field("password") String password, @Field("email") String email);

    @FormUrlEncoded
    @POST("/login")
    Call<AnswerBody> signIn(@Field("email") String email, @Field("password") String password);

    @GET("/users")
    Call<User> getUser(@Query("email") String email);

    @GET("/logout")
    Call<AnswerBody> logout();
}
