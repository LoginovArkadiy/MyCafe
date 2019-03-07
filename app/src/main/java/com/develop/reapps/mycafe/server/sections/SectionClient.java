package com.develop.reapps.mycafe.server.sections;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

public class SectionClient {
    private static SectionsService sectionsService;
    private Context context;

    public SectionClient(Context context) {
        Retrofit retrofit = Requests.getClient();
        sectionsService = retrofit.create(SectionsService.class);
        this.context = context;
    }

    public void loadSection(String section) {
        Call<AnswerBody> call = sectionsService.loadSection(section);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                if (response.isSuccessful()) Requests.makeToastNotification(context, "Successful");
                else Requests.makeToastNotification(context, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });
    }

    public Section[] getSections() {
        SectionGetTask task = new SectionGetTask();
        task.execute();
        try {
            return task.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Requests.makeToastNotification(context, "Прошло недостаточно времени");
        }
        return new Section[0];
    }

    public void delete(int id) {
        Call<AnswerBody> call = sectionsService.delete(id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response);
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });
    }

    private static class SectionGetTask extends AsyncTask<Void, Void, Section[]> {
        @Override
        protected Section[] doInBackground(Void... voids) {
            Call<Section[]> call = sectionsService.getSections();
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Section[0];
        }
    }
}
