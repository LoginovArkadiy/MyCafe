package com.develop.loginov.mycafe.server.news;

import com.develop.loginov.mycafe.news.New;
import com.develop.loginov.mycafe.server.AnswerBody;
import com.develop.loginov.mycafe.server.Requests;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsService {
    //новая новость
    @POST("/news/")
    Call<AnswerBody> loadNew(@Field("title") String title,
                             @Field("description") String description,
                             @Field("image") byte[] image,
                             @Field("time") String time);

    //все новости
    @GET("/news/")
    Call<New[]> getNews();

    //удалить новость
    @GET("/news/delete/{id}")
    Call<AnswerBody> deleteNew(@Path("id") int id);

    //дать новость id
    @GET("/news/get/{id}")
    Call<New> getNewById(@Path("id") int id);

    Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

}
