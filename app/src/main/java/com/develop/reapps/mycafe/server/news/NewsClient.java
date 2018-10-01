package com.develop.reapps.mycafe.server.news;

import android.content.Context;
import android.os.AsyncTask;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.news.New;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

    public void loadNew(String name, String description, String date, File file) {
        New myNew = new New(name, description, date, file);
        NewPostTask task = new NewPostTask();
        task.execute(myNew);
        try {
            Requests.makeToastNotification(context, task.get(10, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "Timeout");
        }
    }

    public New[] getNews() {
        NewGetTask task = new NewGetTask();
        task.execute();
        try {
            return task.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "TimeOut News");
        }

        return new New[0];
    }

    private static class NewPostTask extends AsyncTask<New, Void, Integer> {

        @Override
        protected Integer doInBackground(New... news) {
            New myNew = news[0];
            UploadsService uploadsService = retrofit.create(UploadsService.class);

            String title = myNew.getName();
            String description = myNew.getDescription();
            String time = myNew.getTime();
            File f = myNew.getFile();

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", title + ".png", reqFile);
            RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);

            try {
                Call<AnswerBody> uploadCall = uploadsService.loadPicture3(body, reqDescription);
                Integer imageId = Objects.requireNonNull(uploadCall.execute().body()).id;
                Call<AnswerBody> call = newsService.loadNew(title, description);
                Integer id = Objects.requireNonNull(call.execute().body()).id;
                Call<AnswerBody> call2 = newsService.putImage(id, imageId);
                return call2.execute().code();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    private static class NewGetTask extends AsyncTask<Void, Void, New[]> {

        @Override
        protected New[] doInBackground(Void... voids) {
            Call<New[]> call = newsService.getNews();
            try {
                New[] news = call.execute().body();
                if (news == null) news = new New[0];
                return news;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new New[0];
        }
    }
}
