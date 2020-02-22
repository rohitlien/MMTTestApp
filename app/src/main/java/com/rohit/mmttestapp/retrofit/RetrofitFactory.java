package com.rohit.mmttestapp.retrofit;


import com.rohit.mmttestapp.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static Retrofit retrofit = null;

    private RetrofitFactory() {
    }

    public static Retrofit getRetrofit() {

        if(retrofit == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();


            client.connectTimeout(60, TimeUnit.SECONDS);
            client.readTimeout(60, TimeUnit.SECONDS);
            client.writeTimeout(60, TimeUnit.SECONDS);

            client.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request original = chain.request();
                    String version = BuildConfig.VERSION_NAME;
                    Request request = original.newBuilder()
                            .header("os", "android")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            retrofit = new Retrofit.Builder().client(client.build()).baseUrl(AppConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
