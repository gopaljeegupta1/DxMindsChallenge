package com.gopal.dxmindschallenge.networking;

import androidx.lifecycle.MutableLiveData;

import com.gopal.dxmindschallenge.model.NewsResponse;
import com.gopal.dxmindschallenge.model.NewsSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private NewsApi newsApi;
    private static NewsRepository newsRepository;

    public NewsRepository() {
        newsApi = RetrofitService.createService(NewsApi.class);
    }

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<NewsSource> getSourcesData(String key) {
        MutableLiveData<NewsSource> sourceData = new MutableLiveData<>();
        newsApi.getSource(key).enqueue(new Callback<NewsSource>() {
            @Override
            public void onResponse(Call<NewsSource> call, Response<NewsSource> response) {
                if (response.isSuccessful()) {
                    sourceData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsSource> call, Throwable t) {
                sourceData.setValue(null);
            }
        });
        return sourceData;
    }

    public MutableLiveData<NewsResponse> getArticlesData(String source, String key) {
        MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getNewsList(source, key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
}
