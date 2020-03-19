package com.example.news_app.db;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.news_app.models.NewsData;
import com.example.news_app.models.NewsSource;
import com.example.news_app.utils.DateTimeFormatterUtil;


public class NewsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private NewsDBSQLiteHelper dbHelper;
    private String[] allColumns = {NewsDBSQLiteHelper.COLUMN_ID,
            NewsDBSQLiteHelper.COLUMN_SOURCE_ID,
            NewsDBSQLiteHelper.COLUMN_SOURCE_NAME,
            NewsDBSQLiteHelper.COLUMN_TITLE,
            NewsDBSQLiteHelper.COLUMN_DESCRIPTION,
            NewsDBSQLiteHelper.COLUMN_URL,
            NewsDBSQLiteHelper.COLUMN_UTL_TO_IMAGE,
            NewsDBSQLiteHelper.COLUMN_PUBLISHED_AT};

    public NewsDataSource(Context context) {
        dbHelper = new NewsDBSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertNewsArticle(NewsData data) {
        ContentValues values = new ContentValues();
        values.put(NewsDBSQLiteHelper.COLUMN_ID, data.getId());
        values.put(NewsDBSQLiteHelper.COLUMN_SOURCE_ID, data.getSource().getId());
        values.put(NewsDBSQLiteHelper.COLUMN_SOURCE_NAME, data.getSource().getName());
        values.put(NewsDBSQLiteHelper.COLUMN_TITLE, data.getTitle());
        values.put(NewsDBSQLiteHelper.COLUMN_DESCRIPTION, data.getDescription());
        values.put(NewsDBSQLiteHelper.COLUMN_URL, data.getUrl());
        values.put(NewsDBSQLiteHelper.COLUMN_UTL_TO_IMAGE, data.getUrlToImage());
        values.put(NewsDBSQLiteHelper.COLUMN_PUBLISHED_AT, data.getPublishedAt().toString());
        database.insert(NewsDBSQLiteHelper.TABLE_NEWS, null, values);
    }

    public void deleteNewsArticle(NewsData data) {
        String id = data.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(NewsDBSQLiteHelper.TABLE_NEWS, NewsDBSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<NewsData> getAllNews() {
        List<NewsData> news = new ArrayList<NewsData>();

        Cursor cursor = database.query(NewsDBSQLiteHelper.TABLE_NEWS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NewsData newsData = cursorToNewsData(cursor);
            news.add(newsData);
            cursor.moveToNext();
        }
        cursor.close();
        return news;
    }

    private NewsData cursorToNewsData(Cursor cursor) {
        NewsData newsData = new NewsData();
        NewsSource newsSource = new NewsSource();
        newsSource.setId(cursor.getString(1));
        newsSource.setName(cursor.getString(2));
        newsData.setId(cursor.getString(1));
        newsData.setSource(newsSource);
        newsData.setTitle(cursor.getString(3));
        newsData.setDescription(cursor.getString(4));
        newsData.setUrl(cursor.getString(5));
        newsData.setUrlToImage(cursor.getString(6));
        try {
            newsData.setPublishedAt(DateTimeFormatterUtil.formatDate(cursor.getString(7)));
        } catch (ParseException e) {
            newsData.setPublishedAt(new Date());
        }
        return newsData;
    }

}