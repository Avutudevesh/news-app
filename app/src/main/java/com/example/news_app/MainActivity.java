package com.example.news_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.news_app.article.ArticleActivity;
import com.example.news_app.models.NewsData;
import com.example.news_app.view.NewsAdapter;
import com.example.news_app.view.NewsListDiffCallback;
import com.example.news_app.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsList;
    private MainActivityViewModel viewModel;
    private NewsAdapter adapter;
    DiffUtil.ItemCallback<NewsData> diffCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViewModel();
        setUpRecyclerView();
        setUpActionBar();
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        final Observer<ArrayList<NewsData>> newsObserver = new Observer<ArrayList<NewsData>>() {
            @Override
            public void onChanged(ArrayList<NewsData> newsData) {
                adapter.submitList(newsData);
            }
        };
        final Observer<String> clickObserver = new Observer<String>() {
            @Override
            public void onChanged(String url) {
                startActivity(ArticleActivity.getStartIntent(getApplicationContext(), url));
            }
        };
        viewModel.getNewsDataLiveData().observe(this, newsObserver);
        viewModel.getClickedNewsArticleLiveData().observe(this, clickObserver);
        viewModel.fetchNewsData();
    }

    private void setUpRecyclerView() {
        newsList = findViewById(R.id.news_list);
        diffCallback = new NewsListDiffCallback();
        adapter = new NewsAdapter(diffCallback, viewModel);
        newsList.setAdapter(adapter);
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }
}
