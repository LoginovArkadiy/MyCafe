package com.develop.reapps.mycafe.server.workers;

import android.os.AsyncTask;

import com.develop.reapps.mycafe.server.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkerTimePostTask extends AsyncTask<Integer, Void, String> {
    @Override
    protected String doInBackground(Integer... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Requests.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WorkerService service = retrofit.create(WorkerService.class);
        Call<String> call = service.changeWorkTime(params[0], params[1]);
        try {
            return call.execute().body();
        } catch (IOException e) {
        }
        return "ОШИБКА";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
