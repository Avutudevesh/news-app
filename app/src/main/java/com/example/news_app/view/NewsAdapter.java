package com.example.news_app.view;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.news_app.R;
import com.example.news_app.models.NewsData;
import com.example.news_app.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

//Adapter from home screen recycler view
public class NewsAdapter extends ListAdapter<NewsData, NewsViewHolder> {

    public interface CallBack {
        void onSaveArticleClicked(NewsData data);

        void onFilterFromPublisherClicked(NewsData data);
    }

    private MainActivityViewModel viewModel;
    private CallBack callBack = null;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

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
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {
        final NewsData newsItem = getItem(position);
        holder.bind(newsItem, viewModel);
        holder.popMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.popMenuButton);
                popupMenu.inflate(R.menu.card_options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.saveOffline:
                                callBack.onSaveArticleClicked(newsItem);
                                break;
                            case R.id.more_from_publisher:
                                callBack.onFilterFromPublisherClicked(newsItem);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}