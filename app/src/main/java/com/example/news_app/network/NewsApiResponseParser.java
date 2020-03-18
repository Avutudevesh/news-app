package com.example.news_app.network;

import com.example.news_app.models.NewsData;
import com.example.news_app.models.NewsSource;
import com.example.news_app.utils.DateTimeFormatterUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewsApiResponseParser {

    private static final String KEY_ARTICLES = "articles";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_SOURCE_ID = "id";
    private static final String KEY_SOURCE_NAME = "name";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_URL_TO_IMAGE = "urlToImage";
    private static final String KEY_PUBLISHED_AT = "publishedAt";

    public static ArrayList<NewsData> parse(JSONObject json) throws JSONException, ParseException {
        ArrayList<NewsData> newsDataList = new ArrayList<>();
        JSONArray articles = json.getJSONArray(KEY_ARTICLES);
        for (int i = 0; i < articles.length(); i++) {
            Object article = articles.get(i);
            if (article instanceof JSONObject) {
                newsDataList.add(parseNewsData((JSONObject) article));
            }
        }

        return newsDataList;
    }

    private static NewsData parseNewsData(JSONObject article) throws JSONException, ParseException {
        NewsData newsData = new NewsData();
        newsData.setSource(parseNewsSource(article.getJSONObject(KEY_SOURCE)));
        newsData.setTitle(article.getString(KEY_TITLE));
        newsData.setDescription(article.getString(KEY_DESCRIPTION));
        newsData.setUrl(article.getString(KEY_URL));
        newsData.setUrlToImage(article.getString(KEY_URL_TO_IMAGE));
        newsData.setPublishedAt(DateTimeFormatterUtil.formatDate(article.getString(KEY_PUBLISHED_AT)));
        return newsData;
    }

    private static NewsSource parseNewsSource(JSONObject source) throws JSONException {
        NewsSource newsSource = new NewsSource();
        newsSource.setId(source.getString(KEY_SOURCE_ID));
        newsSource.setName(source.getString(KEY_SOURCE_NAME));
        return newsSource;
    }
}
