package com.develop.reapps.mycafe.server.products;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.sections.SectionsService;
import com.develop.reapps.mycafe.server.uploads.UploadsService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductClient {

    private static Retrofit retrofit;
    private static ProductService productService;
    private Context context;

    public ProductClient(Context context) {
        retrofit = Requests.getClient();
        productService = retrofit.create(ProductService.class);
        this.context = context;
    }

    public void loadProduct(String name, String description, String type, int price, int weight, File f) {
        ProductPostTask task = new ProductPostTask();
        task.execute(new Product(name, description, price, weight, type, f));
        try {
            Requests.makeToastNotification(context, task.get(10, TimeUnit.SECONDS).code());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "TimeOut");
        }
    }


    public Product[] getProductsByType(String type) {
        ProductGetTask task = new ProductGetTask();
        task.execute(type);
        try {
            return task.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();

        }
        return new Product[0];
    }

    private static class ProductPostTask extends AsyncTask<Product, Void, Response<AnswerBody>> {
        @Override
        protected Response<AnswerBody> doInBackground(Product... products) {
            UploadsService uploadsService = retrofit.create(UploadsService.class);
            String name = products[0].getName(), description = products[0].getDescription();
            int price = products[0].getPrice(), weight = products[0].getWeight();
            String type = products[0].getStrType();
            File f = products[0].getFile();

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", name + ".png", reqFile);
            RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
            try {
                Call<AnswerBody> uploadCall = uploadsService.loadPicture3(body, reqDescription);
                Integer imageId = Objects.requireNonNull(uploadCall.execute().body()).id;

                Log.i("Load_Product", "type = " + type + "\n name = " + name + "\n description = " + description + "\n weight = " + weight + "\n price = " + price + "\n imageId = " + imageId);
                Call<AnswerBody> callProduct = productService.loadProduct(type, name, price, description, weight);
                Integer productId = Objects.requireNonNull(callProduct.execute().body()).id;

                Call<AnswerBody> callAccess = productService.setPublicAccess(productId, true);
                callAccess.execute();
                Call<AnswerBody> callEditImage = productService.editImage(productId, imageId);
                return callEditImage.execute();
            } catch (IOException e) {

            }
            return null;
        }


    }

    private static class ProductGetTask extends AsyncTask<String, Void, Product[]> {

        @Override
        protected Product[] doInBackground(String... strings) {
            Call<Product[]> call = productService.getProducts(strings[0]);
            try {
                Response<Product[]> response = call.execute();
                if (response.isSuccessful()) return response.body();
            } catch (IOException e) {

            }
            return new Product[0];
        }
    }
}
