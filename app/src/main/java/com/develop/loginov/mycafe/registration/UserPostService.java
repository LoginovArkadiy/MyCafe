package com.develop.loginov.mycafe.registration;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface UserPostService {
    //  @POST("/signup/{username}/{password}/{email}/")
    // Call<User> loadUser(@Path("username") String username, @Path("password") String password, @Path("email") String email);
    //@FormUrlEncoded

   // @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/signup")
    Call<String> loadUser(@Field("username") String username,
                          @Field("password") String password,
                          @Field("email") String email,
                          @Field("role") int id);
    //Call<String> loadUser(@Body UserRegistrationBody user);
}
