package com.develop.loginov.mycafe.server.products;

import android.os.AsyncTask;
import android.util.Log;

import com.develop.loginov.mycafe.MyBitmapConverter;
import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.server.AnswerBody;
import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.uploads.UploadPostAnswerBody;
import com.develop.loginov.mycafe.server.uploads.UploadsService;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductPostTask extends AsyncTask<Product, Void, AnswerBody> {
    @Override
    protected AnswerBody doInBackground(Product... products) {
        ProductService productService = ProductService.retrofit.create(ProductService.class);
        UploadsService uploadService = ProductService.retrofit.create(UploadsService.class);
        String name = products[0].getName(), description = products[0].getDescription();
        int price = products[0].getPrice(), weight = products[0].getWeight(), type = products[0].getType();
        byte[] image = products[0].getBytes();
        Call<UploadPostAnswerBody> callPicture = uploadService.loadPicture(image, name, ".png");
        AnswerBody answerBody = new AnswerBody();
        answerBody.status = -1;

        callPicture.enqueue(new Callback<UploadPostAnswerBody>() {
            @Override
            public void onResponse(Call<UploadPostAnswerBody> call, Response<UploadPostAnswerBody> response) {
                UploadPostAnswerBody answer = response.body();
                Log.i("RESPONSE", response.code() + "");
                //  Call<AnswerBody> callProduct = productService.loadProduct(name, description, price, weight, type, imageId);

            }

            @Override
            public void onFailure(Call<UploadPostAnswerBody> call, Throwable t) {

            }
        });

     /*   try {
            Response<UploadPostAnswerBody> response = callPicture.execute();
            UploadPostAnswerBody answer = response.body();
            assert answer != null;
            int imageId = answer.imageId;

            Call<AnswerBody> callProduct = productService.loadProduct(name, description, price, weight, type, imageId);
            return callProduct.execute().body();
        } catch (IOException e) {
            return new AnswerBody();
        }*/
        return answerBody;
    }
}
