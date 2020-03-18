package com.example.news_app.article;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.news_app.R;

public class ArticleActivity extends AppCompatActivity {

    private static final String KEY_ARTICLE_URL_EXTRA = "article-url";

    public static Intent getStartIntent(Context context, String url) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(KEY_ARTICLE_URL_EXTRA, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News Article");
        WebView articleWebView = findViewById(R.id.article_webview);
        articleWebView.loadUrl(getIntent().getStringExtra(KEY_ARTICLE_URL_EXTRA));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
