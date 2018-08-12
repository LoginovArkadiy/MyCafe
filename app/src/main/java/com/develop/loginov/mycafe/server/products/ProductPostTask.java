package com.develop.loginov.mycafe.server.products;

import android.os.AsyncTask;

import com.develop.loginov.mycafe.MyBitmapConverter;
import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.server.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductPostTask extends AsyncTask<Product, Void, String> {


    @Override
    protected String doInBackground(Product... products) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        ProductService service = retrofit.create(ProductService.class);

        String name = products[0].getName(), description = products[0].getDescription();
        int price = products[0].getPrice(), weight = products[0].getWeight(), type = products[0].getType();
        byte[] image = products[0].getBytes();
        Call<String> call = service.loadProduct(name, description, price, weight, type, image);
        try {
            Response<String> response = call.execute();
            return response.body();
        } catch (IOException e) {
            return "Exception";
        }
    }


}
