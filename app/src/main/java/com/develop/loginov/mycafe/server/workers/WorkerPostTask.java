package com.develop.loginov.mycafe.server.workers;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.user.User;
import com.develop.loginov.mycafe.server.user.UserService;
import com.develop.loginov.mycafe.workers.Worker;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WorkerPostTask extends AsyncTask<Worker, Void, String> {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(Worker... workers) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        UserService service1 = retrofit.create(UserService.class);
        WorkerService service2 = retrofit.create(WorkerService.class);
        Call<User> callId = service1.getUser(workers[0].getEmail());
        try {
            int id = Objects.requireNonNull(callId.execute().body()).id;
            Call<String> callPostWorker = service2.loadWorker(id, workers[0].getRole(), workers[0].getPost(), workers[0].getBytes());
            return callPostWorker.execute().body();
        } catch (IOException e) {
            return "ОШИБКА";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


}
