package com.develop.loginov.mycafe.server.news;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.news.New;
import com.develop.loginov.mycafe.server.AnswerBody;

import java.io.IOException;

import retrofit2.Call;

public class NewPostTask extends AsyncTask<New, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(New... news) {
        New myNew = news[0];
        NewsService service = NewsService.retrofit.create(NewsService.class);
        String title = myNew.getName();
        String description = myNew.getDescription();
        String time = myNew.getTime();
        byte[] image = myNew.getBytes();
        Call<AnswerBody> call = service.loadNew(title, description, image, time);

        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnswerBody answerBody = new AnswerBody();
        answerBody.status = -1;
        return answerBody;
    }
}
