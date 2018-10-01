package com.develop.reapps.mycafe.server.order;

import android.os.AsyncTask;

import com.develop.reapps.mycafe.server.retrofit.Requests;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class OrdersGetTask extends AsyncTask<Void, Void, String[]> {
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

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
    }
}
