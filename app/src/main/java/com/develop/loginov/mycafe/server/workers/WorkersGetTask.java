package com.develop.loginov.mycafe.server.workers;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.workers.Worker;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WorkersGetTask extends AsyncTask<Void, Void, Worker[]> {
    @Override
    protected Worker[] doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Requests.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WorkerService service = retrofit.create(WorkerService.class);
        Call<Worker[]> call = service.getWorkers();
        try {
            return call.execute().body();
        } catch (IOException e) {
            return new Worker[0];
        }
    }

    @Override
    protected void onPostExecute(Worker[] workers) {
        super.onPostExecute(workers);

    }
}
