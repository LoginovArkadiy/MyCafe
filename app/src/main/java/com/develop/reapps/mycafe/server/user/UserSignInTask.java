package com.develop.reapps.mycafe.server.user;

import android.os.AsyncTask;

import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserSignInTask extends AsyncTask<String, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(String... params) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        UserService service = retrofit.create(UserService.class);
        String password = params[1], email = params[0];
        Call<AnswerBody> call = service.signIn(email, password);
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
