package com.example.nanohealthsuits.NetworkTransaction;

import com.example.nanohealthsuits.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CallToApi {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseOfLogin> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{productId}")
    Call<Product> getProduct(@Path("productId") int productId);
}
