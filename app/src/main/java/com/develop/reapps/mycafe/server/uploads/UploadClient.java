package com.develop.reapps.mycafe.server.uploads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UploadClient {
    private static UploadsService uploadsService;
    private Retrofit retrofit;

    public UploadClient() {
        retrofit = Requests.getClient();
        uploadsService = retrofit.create(UploadsService.class);
    }

    public Bitmap getImageById(int imageId) {
        UploadGetTask task = new UploadGetTask();
        task.execute(imageId);
        try {
            return task.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImageBitmap(ImageView imageView, int imageId) {

        Call<ResponseBody> call = uploadsService.downloadPicture(imageId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful())
                        imageView.setImageBitmap(MyBitmapConverter.getBitmapfromByteArray(Objects.requireNonNull(response.body()).bytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
            }
        });
    }

    private static class UploadGetTask extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            int imageId = integers[0];
            Call<ResponseBody> call = uploadsService.downloadPicture(imageId);
            try {
                ResponseBody responseBody = call.execute().body();
                assert responseBody != null;
                return MyBitmapConverter.getBitmapfromByteArray(responseBody.bytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
