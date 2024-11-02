package com.ganten.ethlistener.feishu;

import com.ganten.ethlistener.feishu.request.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeishuApiService {
    @POST("{token}")
    Call<FeishuResponse> send(@Path("token") String token, @Body Message message);
}
