package com.develop.reapps.mycafe.server.products;

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.server.AnswerBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


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
    Call<List<Product>> getProducts(@Path("type") String type);

    //возвращает продукт по id
    @GET("api/menu/get/id/{id}")
    Call<Product> getProductById(@Path("id") int id);


    @POST("api/menu/{id}/edit/publicaccess/{flag}")
    Call<AnswerBody> setPublicAccess(@Path("id") int id, @Path("flag") boolean flag);

    @POST("api/menu/delete/{id}")
    Call<AnswerBody> delete(@Path("id") int id);


    @FormUrlEncoded
    @POST("api/menu/{id}/edit/image")
    Call<AnswerBody> editImage(@Path("id") int id, @Field("imageId") int imageId);


    @Multipart
    @POST("test/menu")
    Call<AnswerBody> testLoadProduct(@Part MultipartBody.Part body,
                                     @Part("name") RequestBody name,
                                     @Part("description") RequestBody description,
                                     @Part("type") RequestBody type,
                                     @Part("price") RequestBody price,
                                     @Part("weight") RequestBody weight);

}
