package com.develop.loginov.mycafe.registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.UserPostService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    private Context context;
    String username, password, answerHTTP = "", email;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        EditText editPass = findViewById(R.id.edit_pass);
        EditText editLogin = findViewById(R.id.edit_login);
        EditText editEmail = findViewById(R.id.edit_email);
        textView = findViewById(R.id.tv_answer_http);
        findViewById(R.id.button_post).setOnClickListener(v -> {
            username = editLogin.getText().toString();
            password = editPass.getText().toString();
            email = editEmail.getText().toString();
            new MyTask().execute("");
        });

    }


    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            UserPostService service = retrofit.create(UserPostService.class);
            Call<String> call = service.loadUser(username, password, email);

            try {
                Response<String> userResponse = call.execute();
                answerHTTP = userResponse.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (answerHTTP != null) textView.setText(answerHTTP + "!!!");
            else textView.setText("ОШИБОЧКА");
        }


    }
}
