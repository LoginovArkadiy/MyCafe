package com.develop.reapps.mycafe.server.tastes;

import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TasteService {
    /*/tastes (GET) - возвращает ВСЕ вкусы
POST /tastes - параметры: "name", "tobaccoId"
/tastes/get/id/:id - по id
/tastes/gets/tobaccoId/:id - возвращает МАССИВ вкусов соответствующих данному табаку
POST /tastes/:id/edit/(name / tobaccoId)
POST /tastes/delete/:id*/
    @GET("api/tastes/gets/tobaccoId/{id}")
    Call<Taste[]> getTastes(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/tastes")
    Call<AnswerBody> loadTaste(@Field("name") String name, @Field("tobaccoId") int tobaccoId);

    @POST("api/tastes/delete/{id}")
    Call<AnswerBody> delete(@Path("id") int id);
}
