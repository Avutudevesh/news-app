package com.example.news_app.repository;

import android.os.AsyncTask;

import com.example.news_app.network.HttpManager;
import com.example.news_app.network.RequestPackage;

public class NewsRepository extends AsyncTask<String, Integer, String> {

    private String BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";

    @Override
    protected String doInBackground(String... params) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUrl(BASE_URL);
        return HttpManager.getData(requestPackage);
    }
}
