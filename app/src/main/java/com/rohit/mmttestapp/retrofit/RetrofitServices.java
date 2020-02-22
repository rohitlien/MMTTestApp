package com.rohit.mmttestapp.retrofit;


import com.rohit.mmttestapp.pojo.ProductsData;
import com.rohit.mmttestapp.pojo.Variants;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitServices {

    @GET
    Call<ProductsData> getAllProducts(
            @Url String url

    );
}
