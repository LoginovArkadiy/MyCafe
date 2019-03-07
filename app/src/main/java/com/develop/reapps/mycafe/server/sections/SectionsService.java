package com.develop.reapps.mycafe.server.sections;

import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SectionsService {
    @FormUrlEncoded
    @POST("/api/menuSections")
    Call<AnswerBody> loadSection(@Field("type") String type);

    @GET("/api/menuSections")
    Call<Section[]> getSections();

    @POST("api/menuSections/delete/{id}")
    Call<AnswerBody> delete(@Path("id") int id);
}
