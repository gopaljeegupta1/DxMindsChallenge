package com.gopal.dxmindschallenge.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gopal.dxmindschallenge.model.NewsSource;
import com.gopal.dxmindschallenge.networking.NewsApi;
import com.gopal.dxmindschallenge.networking.NewsRepository;
import com.gopal.dxmindschallenge.model.NewsResponse;

public class ArticlesViewModel extends ViewModel {

    private MutableLiveData<NewsSource> mutableLiveDataSource;
    private MutableLiveData<NewsResponse> mutableLiveData;
    private NewsRepository newsRepository;

    public void initSourceApi() {
        if (mutableLiveDataSource != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
        mutableLiveDataSource = newsRepository.getSourcesData(NewsApi.API_KEY);
    }

    public void initArticleApi(String sourceId) {
        if (mutableLiveData != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getArticlesData(sourceId, NewsApi.API_KEY);

    }

    public LiveData<NewsSource> getSourceRepository() {
        return mutableLiveDataSource;
    }

    public LiveData<NewsResponse> getArticleRepository() {
        return mutableLiveData;
    }
}
