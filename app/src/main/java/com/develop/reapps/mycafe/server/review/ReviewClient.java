package com.develop.reapps.mycafe.server.review;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.reviews.Review;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewClient {
    private Context context;
    private static ReviewsService service;

    public ReviewClient(Context context) {
        this.context = context;
        service = Requests.getClient().create(ReviewsService.class);
    }

    public void loadReview(String text, String date) {
        Call<AnswerBody> call = service.loadComment(text, date);
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

    public Review[] getReviews() {
        ReviewsGetTask task = new ReviewsGetTask();
        task.execute();
        try {
            return task.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new Review[0];
    }

    private static class ReviewsGetTask extends AsyncTask<Void, Void, Review[]> {

        @Override
        protected Review[] doInBackground(Void... voids) {
            Call<Review[]> call = service.getComments();
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Review[0];
        }
    }
}
