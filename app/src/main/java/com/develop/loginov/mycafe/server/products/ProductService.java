package com.develop.loginov.mycafe.server.products;

import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.server.AnswerBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ProductService {
    //добавляет новый продукт в меню
    @POST("/menu")
    Call<String> loadProduct(@Field("name") String name,
                             @Field("description") String description,
                             @Field("price") int price,
                             @Field("weight") int weight,
                             @Field("type") int type,
                             @Field("image") byte[] image
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
    Call<AnswerBody> setImage(@Path("id") int id, @Field("imageId") int imageId);

    //все типы продуктов
    @GET("/menuSections")
    Call<AnswerBody> getMenuSections();

    //добавление нового типа
    @POST("/menuSections")
    Call<AnswerBody> addMenuSections(@Field("type") int type);
}
