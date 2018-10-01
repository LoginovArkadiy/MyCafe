package com.develop.reapps.mycafe.server.review;


import com.develop.reapps.mycafe.reviews.Review;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReviewsService {
    //добавление нового отзыва
    @FormUrlEncoded
    @POST("api/comments")
    Call<AnswerBody> loadComment(@Field("text") String text);

    //возваращает все отзывы
    @GET("api/comments")
    Call<Review[]> getComments();

}
