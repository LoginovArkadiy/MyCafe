package com.develop.reapps.mycafe.server.order;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderPostTask extends AsyncTask<String, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(String... params) {
        Retrofit retrofit = Requests.getClient();
        OrderService service = retrofit.create(OrderService.class);
        String order = params[0];
        Call<AnswerBody> call = service.loadOrder(order);
        final AnswerBody[] answerBody = {new AnswerBody()};
        answerBody[0].status = -1;
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                answerBody[0] = response.body();
                assert answerBody[0] != null;
                answerBody[0].status = response.code();
            }
            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });
        return answerBody[0];
    }
}
