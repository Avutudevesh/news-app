package com.example.news_app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app.R;
import com.example.news_app.models.NewsData;
import com.example.news_app.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class NewsAdapter extends ListAdapter<NewsData, NewsViewHolder> {

    private MainActivityViewModel viewModel;

    public NewsAdapter(@NonNull DiffUtil.ItemCallback<NewsData> diffCallback, MainActivityViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsData item = getItem(position);
        holder.bind(item, viewModel);
    }
}