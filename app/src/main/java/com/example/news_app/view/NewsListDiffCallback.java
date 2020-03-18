package com.example.news_app.view;

import com.example.news_app.models.NewsData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class NewsListDiffCallback extends DiffUtil.ItemCallback<NewsData> {

    @Override
    public boolean areItemsTheSame(@NonNull NewsData oldItem, @NonNull NewsData newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull NewsData oldItem, @NonNull NewsData newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }
}