package com.example.news_app.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.news_app.R;
import com.example.news_app.models.NewsData;
import com.example.news_app.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NewsViewHolder extends RecyclerView.ViewHolder {

    public interface SaveArticleCallBack {
        void onSaveArticleClicked(NewsData data);
    }

    NewsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    private SaveArticleCallBack callBack = null;

    public void setCallBack(SaveArticleCallBack callBack) {
        this.callBack = callBack;
    }

    private TextView source = itemView.findViewById(R.id.source);
    private TextView title = itemView.findViewById(R.id.title);
    private TextView publishDate = itemView.findViewById(R.id.publish_date);
    private ImageView newsImage = itemView.findViewById(R.id.headline_image);
    private ImageButton saveImageButton = itemView.findViewById(R.id.saveButton);

    void bind(final NewsData data, final MainActivityViewModel viewModel) {
        source.setText(data.getSource().getName());
        title.setText(data.getTitle());
        publishDate.setText(data.getPublishedAt().toString());
        loadBackgroundImage(data.getUrlToImage());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setClickedNewsArticle(data.getUrl());
            }
        });
        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onSaveArticleClicked(data);
            }
        });
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
