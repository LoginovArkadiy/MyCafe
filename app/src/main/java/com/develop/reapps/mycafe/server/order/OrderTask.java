package com.develop.reapps.mycafe.server.order;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderTask {

    private Retrofit retrofit;
    private static OrderService service;
    private Context context;

    public OrderTask(Context context) {
        retrofit = Requests.getClient();
        service = retrofit.create(OrderService.class);
        this.context = context;
    }

    public void loadOrder(String order) {
        Call<AnswerBody> call = service.loadOrder(order);
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

    public String[] getOrders() {
        OrdersGetTask task = new OrdersGetTask();
        task.execute();
        String[] strings = new String[0];
        try {
            strings = task.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Requests.makeToastNotification(context, "Timeout");
        }

        return strings;
    }

    private static class OrdersGetTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            Retrofit retrofit = Requests.getClient();
            OrderService service = retrofit.create(OrderService.class);
            Call<OrderServer[]> call = service.getOrders();

            try {
                OrderServer[] orderServers = call.execute().body();
                if (orderServers == null) orderServers = new OrderServer[0];
                String[] strings = new String[orderServers.length];
                for (int i = 0; i < orderServers.length; i++) {
                    strings[i] = orderServers[i].order;
                }
                return strings;
            } catch (IOException e) {
                e.printStackTrace();
            }


            return new String[0];
        }


    }

}

