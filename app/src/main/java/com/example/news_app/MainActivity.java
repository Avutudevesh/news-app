package com.example.news_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
                Log.d("TestActivity", Integer.toString(newsData.size()));
            }
        };
        viewModel.getNewsDataLiveData().observe(this, newsObserver);
        viewModel.fetchNewsData();
    }

    private void setUpRecyclerView() {
        newsList = findViewById(R.id.news_list);
        diffCallback = new NewsListDiffCallback();
        adapter = new NewsAdapter(diffCallback);
        newsList.setAdapter(adapter);
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }
}
