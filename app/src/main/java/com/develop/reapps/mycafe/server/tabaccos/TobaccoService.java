package com.develop.reapps.mycafe.server.tabaccos;

import com.develop.reapps.mycafe.menu.element.Tabac;
import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TobaccoService {

    @FormUrlEncoded
    @POST("api/tobacco")
    Call<AnswerBody> loadTobacco(@Field("name") String name, @Field("price") int price, @Field("description") String description);

    @FormUrlEncoded
    @POST("api/tobacco/{id}/edit/name")
    Call<AnswerBody> editName(@Path("id") int id, @Field("name") String name);

    @FormUrlEncoded
    @POST("api/tobacco/{id}/edit/price")
    Call<AnswerBody> editPrice(@Path("id") int id, @Field("price") int price);

    @FormUrlEncoded
    @POST("api/tobacco/{id}/edit/description")
    Call<AnswerBody> editDescription(@Path("id") int id, @Field("description") String description);

    @FormUrlEncoded
    @POST("api/tobacco/{id}/edit/imageId")
    Call<AnswerBody> editImage(@Path("id") int id, @Field("imageId") int imageId);

    @POST("api/tobacco/delete/{id}")
    Call<AnswerBody> delete(@Path("id") int id);

    @GET("api/tobacco")
    Call<Tabac[]> getTabaccos();
}
