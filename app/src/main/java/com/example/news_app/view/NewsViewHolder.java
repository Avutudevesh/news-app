package com.example.news_app.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    private ImageView newsImage = itemView.findViewById(R.id.headline_image);

    void bind(NewsData data) {
        source.setText(data.getSource().getName());
        title.setText(data.getTitle());
        publishDate.setText(data.getPublishedAt().toString());
        loadBackgroundImage(data.getUrlToImage());
    }

    private void loadBackgroundImage(String url) {
        if (url != null) {
            Glide.with(itemView)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .fitCenter()
                    .into(newsImage);
        }

    }
}
