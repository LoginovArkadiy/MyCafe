package com.develop.loginov.mycafe.server.uploads;

import com.develop.loginov.mycafe.server.products.ProductPostBodyAnswer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UploadsService {
    @FormUrlEncoded
    @POST("/uploads/image/menu")
    Call<UploadPostAnswerBody> loadPicture(@Field("file") byte[] file, @Field("filename") String filename, @Field("extension") String extension);


}
