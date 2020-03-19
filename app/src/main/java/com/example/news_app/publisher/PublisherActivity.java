package com.example.news_app.publisher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.news_app.R;
import com.example.news_app.article.ArticleActivity;
import com.example.news_app.models.NewsData;
import com.example.news_app.view.NewsListDiffCallback;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PublisherActivity extends AppCompatActivity implements PublisherAdapter.CallBack {

    private static final String KEY_PUBLISHER_ARTICLES = "publisher_articles";
    private static final String KEY_PUBLISHER_NAME = "publisher_name";

    public static Intent getStartIntent(Context context, ArrayList<NewsData> newsFromPublisher, String publisherName) {
        Intent intent = new Intent(context, PublisherActivity.class);
        intent.putExtra(KEY_PUBLISHER_ARTICLES, newsFromPublisher);
        intent.putExtra(KEY_PUBLISHER_NAME, publisherName);
        return intent;
    }

    private RecyclerView publisherList;
    DiffUtil.ItemCallback<NewsData> diffCallback;
    private PublisherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_PUBLISHER_NAME));
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        publisherList = findViewById(R.id.publisher_list);
        diffCallback = new NewsListDiffCallback();
        adapter = new PublisherAdapter(diffCallback);
        adapter.setCallBack(this);
        publisherList.setAdapter(adapter);
        adapter.submitList((ArrayList<NewsData>) getIntent().getSerializableExtra(KEY_PUBLISHER_ARTICLES));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onArticleClicked(NewsData data) {
        startActivity(ArticleActivity.getStartIntent(this, data.getUrl()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
