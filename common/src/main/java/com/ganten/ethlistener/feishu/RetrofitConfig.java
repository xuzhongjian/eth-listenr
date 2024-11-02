package com.ganten.ethlistener.feishu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Value("${feishu.url}")
    private String feishuUrl;

    @Bean
    public ObjectMapper retrofitObjectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Bean
    public FeishuApiService apiService(ObjectMapper retrofitObjectMapper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(feishuUrl)
                .addConverterFactory(JacksonConverterFactory.create(retrofitObjectMapper))
                .build();
        return retrofit.create(FeishuApiService.class);
    }
}