package com.example.news_app;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class NewsApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}