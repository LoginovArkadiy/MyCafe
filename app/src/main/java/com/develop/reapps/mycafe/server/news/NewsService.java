package com.develop.reapps.mycafe.server.news;

import com.develop.reapps.mycafe.news.New;
import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsService {
    //новая новость
    //editing header text image
    @FormUrlEncoded
    @POST("/api/news")
    Call<AnswerBody> loadNew(@Field("header") String header,
                             @Field("text") String text,
                             @Field("date") String date);

    @FormUrlEncoded
    @POST("/api/news/{id}/edit/image")
    Call<AnswerBody> putImage(@Path("id") int id, @Field("imageId") int imageId);


    //все новости
    @GET("/api/news/")
    Call<New[]> getNews();

    //удалить новость
    @GET("api/news/delete/{id}")
    Call<AnswerBody> deleteNew(@Path("id") int id);

    //дать новость id
    @GET("api/news/get/{id}")
    Call<New> getNewById(@Path("id") int id);


}
