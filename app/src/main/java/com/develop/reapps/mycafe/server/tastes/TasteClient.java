package com.develop.reapps.mycafe.server.tastes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.products.ProductService;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TasteClient {
    private static Retrofit retrofit;
    private static TasteService tasteService;
    private Context context;

    public TasteClient(Context context) {
        retrofit = Requests.getClient();
        tasteService = retrofit.create(TasteService.class);
        this.context = context;
    }

    public void loadTaste(String taste, int id) {
        Call<AnswerBody> call = tasteService.loadTaste(taste, id);
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

    public void delete(int id) {
        Call<AnswerBody> call = tasteService.delete(id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
                Requests.makeToastNotification(context, "Что то не так");
            }
        });
    }

    public Taste[] getTastes(int id) {
        TasteGetTask task = new TasteGetTask();
        task.execute(id);
        try {
            Requests.makeToastNotification(context, 200);
            return task.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "TimeOut Tastes");
        }

        return new Taste[0];
    }

    private static class TasteGetTask extends AsyncTask<Integer, Void, Taste[]> {

        @Override
        protected Taste[] doInBackground(Integer... integers) {
            Call<Taste[]> call = tasteService.getTastes(integers[0]);
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Taste[0];
        }
    }
}

