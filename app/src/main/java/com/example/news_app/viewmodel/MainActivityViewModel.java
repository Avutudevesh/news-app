package com.example.news_app.viewmodel;

import com.example.news_app.models.NewsData;
import com.example.news_app.repository.NewsRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private NewsRepository repository = new NewsRepository();

    private LiveData<ArrayList<NewsData>> newsDataLiveData = repository.getNewsDataLiveData();

    public void fetchNewsData() {
        repository.execute();
    }

    public LiveData<ArrayList<NewsData>> getNewsDataLiveData() {
        return newsDataLiveData;
    }
}
