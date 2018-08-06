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
        private final String baseUrl = "http://62.109.23.83";

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            UserPostService service = retrofit.create(UserPostService.class);
            UserRegistrationBody body = new UserRegistrationBody();
            body.username = username;
            body.password = password;
            body.email = email;
            Call<String> call = service.loadUser(username, password, email);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if(response.isSuccessful()){
                        Log.i("ANSWER", "sUCCESs");
                        //textView.setText("");
                    } else {
                        Log.i("ANSWER", "notsUCCESs");
                    }
                    answerHTTP = response.body();
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.i("ANSWER", "NOOOO");
                }
            });
            // Log.i("ANSWER",response.isSuccessful() ? "НОРМ" : "ФУУУ" ) ;
          /*  try {
                Response<String> userResponse = call.execute();
                answerHTTP = userResponse.body();
                //  assert userFromServer != null;
                //answerHTTP = userFromServer.username + "\n" + userFromServer.email + "\n" + userFromServer.password;
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (answerHTTP != null) textView.setText(answerHTTP);
            else textView.setText("ОШИБОЧКА");
        }


    }
}
