package com.example.news_app.viewmodel;

import com.example.news_app.models.NewsData;
import com.example.news_app.repository.NewsRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private NewsRepository repository = new NewsRepository();

    private LiveData<ArrayList<NewsData>> newsDataLiveData = repository.getNewsDataLiveData();

    private MutableLiveData<String> clickedNewsArticle = new MutableLiveData<>();

    public void fetchNewsData() {
        repository.execute();
    }

    public LiveData<ArrayList<NewsData>> getNewsDataLiveData() {
        return newsDataLiveData;
    }

    public LiveData<String> getClickedNewsArticleLiveData() {
        return clickedNewsArticle;
    }

    public void setClickedNewsArticle(String url) {
        clickedNewsArticle.postValue(url);
    }

    public void sortOldToNew() {
        repository.sortOldToNew();
    }

    public void sortNewToOld() {
        repository.sortNewToOld();
    }
}
