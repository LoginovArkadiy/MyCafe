package com.develop.reapps.mycafe.server.order;

import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrderService {
    @FormUrlEncoded
    @POST("api/orders")
    Call<AnswerBody> loadOrder(@Field("order") String order);

    @GET("api/orders")
    Call<OrderServer[]> getOrders();
}
