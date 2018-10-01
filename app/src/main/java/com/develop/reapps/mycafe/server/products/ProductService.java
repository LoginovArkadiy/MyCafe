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
    @GET("api/menu")
    Call<Product[]> getProducts(@Query("type") String type);

    //возвращает продукт по id
    @GET("/menu/get/id/{id}")
    Call<Product> getProductById(@Path("id") int id);

    //возвращает список продуктов по типу
    @GET("/menu/gets/type/{type}")
    Call<Product[]> getProductsByType(@Path("type") int type);

    @FormUrlEncoded
    @POST("/menu/{id}/edit/image")
    Call<AnswerBody> editImage(@Path("id") int id, @Field("imageId") int imageId);

    //все типы продуктов
    @GET("/menuSections")
    Call<AnswerBody> getMenuSections();

    //добавление нового типа
    @POST("/menuSections")
    Call<AnswerBody> addMenuSections(@Field("type") int type);

}
