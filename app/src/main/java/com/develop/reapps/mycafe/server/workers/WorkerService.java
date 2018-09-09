package com.develop.reapps.mycafe.server.workers;

import com.develop.reapps.mycafe.workers.Worker;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WorkerService {
    //добавление нового сотрудника
    @POST("/workers")
    Call<String> loadWorker(@Field("id") int id,
                         @Field("role") int role,
                         @Field("post") String post,
                         @Field("imageId") int imageId
    );

    @GET("/workers")
    Call<Worker[]> getWorkers();


    @POST("/worktime")
    Call<String> changeWorkTime(@Field("iswork") int iswork,
                                @Field("id") int id
    );
}
