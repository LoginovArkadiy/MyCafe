package com.develop.reapps.mycafe.server.uploads;

import com.develop.reapps.mycafe.server.AnswerBody;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UploadsService {
    @Multipart
    @POST("api/upload")
    Call<AnswerBody> loadPicture3(@Part MultipartBody.Part image, @Part("description") RequestBody description);

    @GET("api/download/id/{imageId}")
    Call<ResponseBody> downloadPicture(@Path("imageId") int imageId);

}
