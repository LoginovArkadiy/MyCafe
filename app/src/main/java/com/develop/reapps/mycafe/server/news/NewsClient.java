package com.develop.reapps.mycafe.server.news;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.MyDateConverter;
import com.develop.reapps.mycafe.news.New;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

public class NewsClient {
    private static Context context;
    private static Retrofit retrofit;
    private static NewsService newsService;

    public NewsClient(Context context) {
        this.context = context;
        retrofit = Requests.getClient();
        newsService = retrofit.create(NewsService.class);
    }

    public NewsClient() {
        this(MainActivity.getAppContext());
    }

    public void loadNew(String name, String description, File file) {
        String date = MyDateConverter.getMoment();
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), reqFile);
        RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        RequestBody reqName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reqDate = RequestBody.create(MediaType.parse("multipart/form-data"), date);
        Call<AnswerBody> callNews = newsService.testLoadNew(reqName, reqDescription, reqDate, body);
        callNews.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code() + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });
    }

    public void getNews(List<New> list, RecyclerView.Adapter adapter) {
        Call<List<New>> call = newsService.getNews();

        call.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(@NonNull Call<List<New>> call, @NonNull Response<List<New>> response) {
                Requests.makeToastNotification(context, response.code() + " " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<New>> call, @NonNull Throwable t) {
            }
        });
    }

}
