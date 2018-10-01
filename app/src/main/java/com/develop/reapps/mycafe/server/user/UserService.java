package com.develop.reapps.mycafe.server.user;

import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {
    @FormUrlEncoded
    @POST("/api/account/signup")
    Call<AnswerBody> loadUser(@Field("login") String login, @Field("password") String password, @Field("email") String email);

    @FormUrlEncoded
    @POST("/api/account/login")
    Call<AnswerBody> signIn(@Field("email") String email, @Field("password") String password);

    @GET("/api/users/get/email/{email}")
    Call<User> getUser(@Path("email") String email);

    @GET("/api/users/get/id/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("/api/account/logout")
    Call<AnswerBody> logout();


}
