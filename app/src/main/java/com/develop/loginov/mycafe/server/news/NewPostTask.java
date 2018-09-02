package com.develop.loginov.mycafe.server.news;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.news.New;
import com.develop.loginov.mycafe.server.AnswerBody;
import com.develop.loginov.mycafe.server.uploads.UploadPostAnswerBody;
import com.develop.loginov.mycafe.server.uploads.UploadsService;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;

public class NewPostTask extends AsyncTask<New, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(New... news) {
        New myNew = news[0];
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        UploadsService uploadsService = NewsService.retrofit.create(UploadsService.class);

        String title = myNew.getName();
        String description = myNew.getDescription();
        String time = myNew.getTime();
        byte[] image = myNew.getBytes();

        Call<UploadPostAnswerBody> uploadCall = uploadsService.loadPicture(image, title, ".png");

        try {
            int imageId = Objects.requireNonNull(uploadCall.execute().body()).imageId;
            Call<AnswerBody> call = newsService.loadNew(title, description, imageId, time);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnswerBody answerBody = new AnswerBody();
        answerBody.status = -1;
        return answerBody;
    }
}
