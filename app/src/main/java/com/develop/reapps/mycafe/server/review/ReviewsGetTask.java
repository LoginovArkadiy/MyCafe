package com.develop.reapps.mycafe.server.review;

import android.os.AsyncTask;

import com.develop.reapps.mycafe.reviews.Review;

import java.io.IOException;

import retrofit2.Call;

public class ReviewsGetTask extends AsyncTask<Void, Void, Review[]> {
    @Override
    protected Review[] doInBackground(Void... voids) {
        ReviewsService service = ReviewsService.retrofit.create(ReviewsService.class);
        Call<Review[]> call = service.getComments();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Review[0];
    }
}
