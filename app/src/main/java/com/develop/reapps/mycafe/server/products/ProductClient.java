package com.develop.reapps.mycafe.server.products;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.uploads.UploadsService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

    public void testLoadProduct(String name, String description, String type, int price, int weight, File file) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), reqFile);
        RequestBody reqDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        RequestBody reqName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reqType = RequestBody.create(MediaType.parse("multipart/form-data"), type);
        RequestBody reqPrice = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(price));
        RequestBody reqWeight = RequestBody.create(MediaType.parse("multipart/form-data"), Integer.toString(weight));

        Call<AnswerBody> call = productService.testLoadProduct(body, reqName, reqDescription, reqType, reqPrice, reqWeight);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code() + " " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    setProductAccess(response.body().id);
                }

            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });
    }


    private void setProductAccess(int id) {
        Call<AnswerBody> call = productService.setPublicAccess(id, true);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, "Access " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {
            }
        });

    }

    public void delete(int id) {
        Call<AnswerBody> call = productService.delete(id);
        call.enqueue(new Callback<AnswerBody>() {
            @Override
            public void onResponse(@NonNull Call<AnswerBody> call, @NonNull Response<AnswerBody> response) {
                Requests.makeToastNotification(context, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AnswerBody> call, @NonNull Throwable t) {

            }
        });

    }


    public void getProductsByType(String type, List<Product> productList, RecyclerView.Adapter adapter) {
        Call<List<Product>> call = productService.getProducts(type);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Requests.makeToastNotification(context, response.code() + " " + response.message());
                    productList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
            }
        });
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

}
