package com.develop.loginov.mycafe.server.products;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.server.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuGetTask extends AsyncTask<Integer, Void, Product[]> {
    @Override
    protected Product[] doInBackground(Integer... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Requests.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService service = retrofit.create(ProductService.class);
        Call<Product[]> call = service.getProducts(params[0]);
        try {
            return call.execute().body();
        } catch (IOException e) {

        }
        return new Product[0];
    }

    @Override
    protected void onPostExecute(Product[] products) {
        super.onPostExecute(products);
    }
}
