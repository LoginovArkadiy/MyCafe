package com.develop.reapps.mycafe.server.products;

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ProductService {
    //добавляет новый продукт в меню
    @FormUrlEncoded
    @POST("api/menu")
    Call<AnswerBody> loadProduct(@Field("type") String type,
                                 @Field("name") String name,
                                 @Field("price") int price,
                                 @Field("description") String description,
                                 @Field("weight") int weight
    );

    //все продукты
    @GET("api/menu/gets/type/{type}")
    Call<Product[]> getProducts(@Path("type") String type);

    //возвращает продукт по id
    @GET("api/menu/get/id/{id}")
    Call<Product> getProductById(@Path("id") int id);


    @POST("api/menu/{id}/edit/publicaccess/{flag}")
    Call<AnswerBody> setPublicAccess(@Path("id") int id, @Path("flag") boolean flag);

    @FormUrlEncoded
    @POST("api/menu/{id}/edit/image")
    Call<AnswerBody> editImage(@Path("id") int id, @Field("imageId") int imageId);


}
