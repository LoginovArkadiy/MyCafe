package com.develop.reapps.mycafe.server.news;

import com.develop.reapps.mycafe.news.New;
import com.develop.reapps.mycafe.server.AnswerBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<AnswerBody> putImage(@Path("id") int id,
                              @Field("imageId") int imageId);


    //все новости
    @GET("/api/news/")
    Call<List<New>> getNews();

    //удалить новость
    @GET("api/news/delete/{id}")
    Call<AnswerBody> deleteNew(@Path("id") int id);

    //дать новость id
    @GET("api/news/get/{id}")
    Call<New> getNewById(@Path("id") int id);

    @Multipart
    @POST("/test/news")
    Call<AnswerBody> testLoadNew(@Part("header") RequestBody header,
                                 @Part("text") RequestBody text,
                                 @Part("date") RequestBody date,
                                 @Part MultipartBody.Part file);
}
