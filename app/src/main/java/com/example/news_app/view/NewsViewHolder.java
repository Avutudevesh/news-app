package com.example.news_app.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news_app.R;
import com.example.news_app.models.NewsData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewsViewHolder extends RecyclerView.ViewHolder {

    NewsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    private TextView source = itemView.findViewById(R.id.source);
    private TextView title = itemView.findViewById(R.id.title);
    private TextView publishDate = itemView.findViewById(R.id.publish_date);
    private ImageView headlineImage = itemView.findViewById(R.id.headline_image);

    void bind(NewsData data) {
        source.setText(data.getSource().getName());
        title.setText(data.getTitle());
        publishDate.setText(data.getPublishedAt().toString());
    }
}
