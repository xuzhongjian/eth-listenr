package com.ganten.ethlistener.http;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("path/to/your/endpoint")
    Call<String> getExample();

    @POST("path/to/your/endpoint")
    Call<String> postExample(@Body String request);
}
