package com.example.news_app.publisher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_app.R;
import com.example.news_app.models.NewsData;
import com.example.news_app.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class PublisherAdapter extends ListAdapter<NewsData, PublisherViewHolder> {

    public interface CallBack {
        void onArticleClicked(NewsData data);
    }

    private MainActivityViewModel viewModel;
    private CallBack callBack = null;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public PublisherAdapter(@NonNull DiffUtil.ItemCallback<NewsData> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public PublisherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new PublisherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PublisherViewHolder holder, final int position) {
        final NewsData newsItem = getItem(position);
        holder.bind(newsItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onArticleClicked(newsItem);
            }
        });
    }
}