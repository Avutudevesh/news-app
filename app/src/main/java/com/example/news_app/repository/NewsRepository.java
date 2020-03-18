package com.example.news_app.repository;

import android.os.AsyncTask;

import com.example.news_app.models.NewsData;
import com.example.news_app.network.HttpManager;
import com.example.news_app.network.NewsApiResponseParser;
import com.example.news_app.network.RequestPackage;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NewsRepository extends AsyncTask<String, Integer, String> {

    private final String BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";
    private MutableLiveData<ArrayList<NewsData>> newsDataLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<NewsData>> getNewsDataLiveData() {
        return newsDataLiveData;
    }

    @Override
    protected String doInBackground(String... params) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(BASE_URL);
        return HttpManager.getData(requestPackage);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            ArrayList<NewsData> newsData = NewsApiResponseParser.parse(jsonObject);
            newsDataLiveData.postValue(newsData);
        } catch (Exception e) {
            newsDataLiveData.postValue(new ArrayList<NewsData>());
            e.printStackTrace();
        }
    }
}
