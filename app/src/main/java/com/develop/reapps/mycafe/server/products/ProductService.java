package com.develop.reapps.mycafe.server.products;

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.server.AnswerBody;
import com.develop.reapps.mycafe.server.Requests;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ProductService {
    //добавляет новый продукт в меню
    @FormUrlEncoded
    @POST("/menu")
    Call<AnswerBody> loadProduct(@Field("name") String name,
                             @Field("description") String description,
                             @Field("price") int price,
                             @Field("weight") int weight,
                             @Field("type") int type,
                             @Field("imageId") int imageId
    );

    //все продукты
    @GET("/menu")
    Call<Product[]> getProducts(@Query("id") int type);

    //возвращает продукт по id
    @GET("/menu/get/id/{id}")
    Call<Product> getProductById(@Path("id") int id);

    //возвращает список продуктов по типу
    @GET("/menu/gets/type/{type}")
    Call<Product[]> getProductsByType(@Path("type") int type);

    @FormUrlEncoded
    @POST("/menu/{id}/edit/image")
    Call<AnswerBody> setImage(@Path("id") int id, @Field("image") byte[] image);

    //все типы продуктов
    @GET("/menuSections")
    Call<AnswerBody> getMenuSections();

    //добавление нового типа
    @POST("/menuSections")
    Call<AnswerBody> addMenuSections(@Field("type") int type);


    Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();


}
