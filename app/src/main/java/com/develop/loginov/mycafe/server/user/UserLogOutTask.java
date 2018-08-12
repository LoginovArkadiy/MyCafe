package com.develop.loginov.mycafe.server.user;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.server.AnswerBody;
import com.develop.loginov.mycafe.server.Requests;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogOutTask extends AsyncTask<Void, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        UserService service = retrofit.create(UserService.class);
        Call<AnswerBody> call = service.logout();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            AnswerBody answerBody = new AnswerBody();
            answerBody.status = -1;
            return answerBody;
        }
    }
}
