package com.develop.loginov.mycafe.server;

import android.os.AsyncTask;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerTask extends AsyncTask<Integer, Void, Response> {

    private Retrofit retrofit;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String baseUrl = "http://62.109.23.83";
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Override
    protected Response doInBackground(Integer... typeRequest) {
        switch (typeRequest[0]) {
            case Requests.SIGN_UP:
                signUp();
                break;
            case Requests.SIGN_IN:
                signIn();
                break;
            case Requests.POST_PRODUCT:
                break;
            case Requests.POST_WORKER:
                break;
            case Requests.POST_WORK_TIME:
                break;
            case Requests.GET_ALL_WORKERS:
                break;
            case Requests.GET_PRODUCTS_BY_TYPE:
                break;
        }
        return null;
    }

    private void signIn() {
    }

    private void signUp() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
    }



}
