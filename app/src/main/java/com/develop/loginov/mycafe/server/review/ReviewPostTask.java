package com.develop.loginov.mycafe.server.review;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.reviews.Review;
import com.develop.loginov.mycafe.server.AnswerBody;

import java.io.IOException;

import retrofit2.Call;

public class ReviewPostTask extends AsyncTask<String, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(String... params) {
        ReviewsService service = ReviewsService.retrofit.create(ReviewsService.class);
        String text = params[0], date = params[1];
        Call<AnswerBody> call = service.loadComment(text, date);
        AnswerBody answerBody = new AnswerBody();
        answerBody.status = -1;
        try {
            answerBody = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answerBody;
    }
}
