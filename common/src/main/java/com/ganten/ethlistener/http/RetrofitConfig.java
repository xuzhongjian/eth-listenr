package com.ganten.ethlistener.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    private static final String BASE_URL = "https://api.yourservice.com/";

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
