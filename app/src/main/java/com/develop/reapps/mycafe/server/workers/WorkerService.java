package com.develop.reapps.mycafe.server.workers;

import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.workers.Worker;

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

public interface WorkerService {
    //добавление нового сотрудника
    @FormUrlEncoded
    @POST("api/workers")
    Call<AnswerBody> loadWorker(@Field("userId") int id, @Field("post") String post);

    @FormUrlEncoded
    @POST("api/workers/{id}/edit/image")
    Call<AnswerBody> editImage(@Path("id") int id, @Field("imageId") int imageId);

    @FormUrlEncoded
    @POST("api/workers/{id}/edit/role")
    Call<AnswerBody> editRole(@Path("id") int id, @Field("role") int role);

    @GET("/api/workers")
    Call<List<Worker>> getWorkers();

    @POST("api/workers/{id}/edit/isworktoday/{flag}")
    Call<AnswerBody> changeWorkTime(@Path("flag") boolean flag, @Path("id") int id);


    @Multipart
    @POST("test/workers")
    Call<AnswerBody> loadWorker2(@Part("userId") RequestBody id,
                                 @Part("post") RequestBody post,
                                 @Part MultipartBody.Part image
                                 );


}
