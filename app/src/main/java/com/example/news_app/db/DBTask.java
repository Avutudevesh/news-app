package com.example.news_app.db;

import com.example.news_app.models.NewsData;

public class DBTask {
    private NewsDataSource dataSource;
    private Task task;
    private NewsData data;

    public NewsDataSource getDataSource() {
        return dataSource;
    }

    public Task getTask() {
        return task;
    }

    public NewsData getData() {
        return data;
    }

    public enum Task {
        DATABASE_DELETE, DATABASE_ADD, DATABASE_FETCH_ALL
    }

    public DBTask(NewsDataSource dataSource, Task task, NewsData data) {
        this.dataSource = dataSource;
        this.task = task;
        this.data = data;
    }

}
