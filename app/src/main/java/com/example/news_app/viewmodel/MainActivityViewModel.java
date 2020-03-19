package com.example.news_app.viewmodel;

import com.example.news_app.db.DBTask;
import com.example.news_app.db.NewsDBRepository;
import com.example.news_app.models.NewsData;
import com.example.news_app.repository.NewsRepository;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel implements NewsRepository.CallBack, NewsDBRepository.CallBack {

    private NewsRepository repository = null;

    private NewsDBRepository dbRepository = null;

    private MutableLiveData<List<NewsData>> newsDataLiveData = new MutableLiveData<>();

    private MutableLiveData<List<NewsData>> dbNewsLiveData = new MutableLiveData<>();

    private MutableLiveData<String> clickedNewsArticle = new MutableLiveData<>();

    private MutableLiveData<Boolean> errorLiveData = new MutableLiveData<>();

    public void fetchNewsData() {
        repository = new NewsRepository();
        repository.setCallback(this);
        repository.execute();
    }

    public LiveData<List<NewsData>> getNewsDataLiveData() {
        return newsDataLiveData;
    }

    public LiveData<String> getClickedNewsArticleLiveData() {
        return clickedNewsArticle;
    }

    public void setClickedNewsArticle(String url) {
        clickedNewsArticle.postValue(url);
    }

    public void sortOldToNew() {
        List<NewsData> list = newsDataLiveData.getValue();
        if (list != null) {
            Collections.sort(list, NewsData.publishDateComparator);
            newsDataLiveData.postValue(list);
        }
    }

    public void sortNewToOld() {
        List<NewsData> list = newsDataLiveData.getValue();
        if (list != null) {
            Collections.sort(list, NewsData.publishDateReverseComparator);
            newsDataLiveData.postValue(list);
        }
    }

    public void performDBOperation(DBTask task) {
        dbRepository = new NewsDBRepository();
        dbRepository.setCallBack(this);
        dbRepository.execute(task);
    }

    public LiveData<List<NewsData>> getDbNewsLiveData() {
        return dbNewsLiveData;
    }

    @Override
    public void onFetchNewsDataSuccess(List<NewsData> data) {
        newsDataLiveData.postValue(data);
    }

    @Override
    public void onFetchDataError() {
        errorLiveData.postValue(true);
    }

    @Override
    public void onFetchNewsDBData(List<NewsData> data) {
        dbNewsLiveData.postValue(data);
    }

    @Override
    public void onFetchNewsDBError() {
        errorLiveData.postValue(true);
    }

    public MutableLiveData<Boolean> getErrorLiveData() {
        return errorLiveData;
    }
}
