package com.gopal.dxmindschallenge.networking;


import com.gopal.dxmindschallenge.model.NewsResponse;
import com.gopal.dxmindschallenge.model.NewsSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    String BASE_URL = "https://newsapi.org/v2/";
    String API_KEY = "f027aa0115ac45308ad600ad963e9095";

    @GET("sources")
    Call<NewsSource> getSource(@Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getNewsList(@Query("q") String newsSource, @Query("apiKey") String apiKey);
}

