package com.develop.reapps.mycafe.server.tabaccos;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.menu.element.Tabac;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TobaccoClient {
    private static Retrofit retrofit;
    private static TobaccoService tobaccoService;
    private Context context;

    public TobaccoClient(Context context) {
        this.context = context;
        retrofit = Requests.getClient();
        tobaccoService = retrofit.create(TobaccoService.class);
    }

    public TobaccoClient() {
        this(MainActivity.getAppContext());
    }

    public void loadTobacco(String name, int price, String description, File file) {
        TobaccoPostTask task = new TobaccoPostTask();
        task.execute(new Tabac(name, description, price, file));
//        Call<AnswerBody> callTobacco = tobaccoService.loadTobacco(name, price, description);
//        callTobacco.enqueue(new Callback<AnswerBody>() {
//            @Override
//            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
//                Log.d("tobacco" , "Load " + response.body());
//                if (response.body() != null) {
//                    uploadFile(response.body().id, file, description);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AnswerBody> call, Throwable t) {
//
//            }
//        });


        try {
            Integer status = task.get(10, TimeUnit.SECONDS);
            Requests.makeToastNotification(context, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


    private void uploadFile(int tobaccoId, File file , String description){
        RequestBody reqDescription = RequestBody.create(MultipartBody.FORM, description);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), reqFile);
        UploadsService uploadsService = retrofit.create(UploadsService.class);
        Log.d("tobacco" , "file1 " );

        Call<AnswerBody> callUploads = uploadsService.loadPicture3(body, reqDescription);
        Log.d("tobacco" , "file2 " );

        callUploads.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Log.d("tobacco" , "file3 " + response.body());

                if (response.body() != null) {
                    editTobacco(tobaccoId, response.body().id);
                }
            }

            @Override
            public void onFailure(Call<AnswerBody> call, Throwable t) {
                Log.d("FILE_FAIL", t.getMessage() + " \n" + t.getLocalizedMessage());
                Requests.makeToastNotification(context, "NO FILE");
                t.printStackTrace();
            }
        });
    }

    private void editTobacco(int tobaccoId, int imageID){
        Call<AnswerBody> callEditImage = tobaccoService.editImage(tobaccoId, imageID);
        callEditImage.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(Call<AnswerBody> call, Response<AnswerBody> response) {
                Requests.makeToastNotification(context, "GOOOOOD");
            }

            @Override
            public void onFailure(Call<AnswerBody> call, Throwable t) {

            }
        });
    }


    public void delete(int id) {
        Call<AnswerBody> call = tobaccoService.delete(id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });
    }


    public Tabac[] getTobaccos() {
        TobaccoGetTask task = new TobaccoGetTask();
        task.execute();
        try {
            return task.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "TimeOut Tobaccos");
        }
        return new Tabac[0];
    }

    private static class TobaccoPostTask extends AsyncTask<Tabac, Void, Integer> {

        @Override
        protected Integer doInBackground(Tabac... tabacs) {
            Tabac tabac = tabacs[0];
            String name = tabac.getName();
            String description = tabac.getName();
            int price = tabac.getPrice();
            File f = tabac.getFile();
            Log.d("TobaccoClient", "file = " + f.getPath());
            RequestBody reqDescription = RequestBody.create(MultipartBody.FORM, description);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/png"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", f.getName(), reqFile);

            Call<AnswerBody> callTobacco = tobaccoService.loadTobacco(name, price, description);
            UploadsService uploadsService = retrofit.create(UploadsService.class);
            try {
                Log.d("TobaccoClient", "tobacco1");
                Response<AnswerBody> response = callTobacco.execute();
                Integer id = Objects.requireNonNull(response.body()).id;

                Log.d("TobaccoClient", "tobaccoID" + " " + id);


//                Map<String, RequestBody> requestBodyMap = new HashMap<>();
//                requestBodyMap.put("description", reqDescription);
//                requestBodyMap.put("file", reqFile);


                Call<AnswerBody> callUploads = uploadsService.loadPicture3(body, reqDescription);
//                Call<AnswerBody> callUploads = uploadsService.upload(requestBodyMap);


                Response<AnswerBody> responseUploads = callUploads.execute();
                Log.d("TobaccoClient", "after");

                if (responseUploads != null) {
                    Integer imageId = null;
                    if (responseUploads.body() != null) {
                        imageId = responseUploads.body().id;
                    }
                    Log.d("TobaccoClient", "imageID" + imageId);
                    Call<AnswerBody> callEditImage = tobaccoService.editImage(id, imageId);
                    return callEditImage.execute().code();
                }


                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private static class TobaccoGetTask extends AsyncTask<Void, Void, Tabac[]> {

        @Override
        protected Tabac[] doInBackground(Void... voids) {
            Call<Tabac[]> call = tobaccoService.getTabaccos();
            try {
                Response<Tabac[]> response = call.execute();
                if (response.code() == 200) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Tabac[0];
        }
    }
}
