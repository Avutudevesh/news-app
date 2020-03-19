package com.example.news_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.news_app.article.ArticleActivity;
import com.example.news_app.db.DBTask;
import com.example.news_app.db.NewsDataSource;
import com.example.news_app.models.NewsData;
import com.example.news_app.view.NewsAdapter;
import com.example.news_app.view.NewsListDiffCallback;
import com.example.news_app.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.CallBack {

    private RecyclerView newsList;
    private MainActivityViewModel viewModel;
    private NewsAdapter adapter;
    DiffUtil.ItemCallback<NewsData> diffCallback;
    NewsDataSource dataSource;
    private boolean offlineDataShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new NewsDataSource(this);
        setContentView(R.layout.activity_main);
        setUpViewModel();
        setUpRecyclerView();
        setUpActionBar();
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        final Observer<List<NewsData>> newsObserver = new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsData) {
                adapter.submitList(newsData);
            }
        };
        final Observer<List<NewsData>> offlineNewsObserver = new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsData) {
                if (offlineDataShown) {
                    adapter.submitList(newsData);
                }
            }
        };
        final Observer<String> clickObserver = new Observer<String>() {
            @Override
            public void onChanged(String url) {
                startActivity(ArticleActivity.getStartIntent(getApplicationContext(), url));
            }
        };
        viewModel.getNewsDataLiveData().observe(this, newsObserver);
        viewModel.getDbNewsLiveData().observe(this, offlineNewsObserver);
        viewModel.getClickedNewsArticleLiveData().observe(this, clickObserver);
        viewModel.fetchNewsData();
    }

    private void setUpRecyclerView() {
        newsList = findViewById(R.id.news_list);
        diffCallback = new NewsListDiffCallback();
        adapter = new NewsAdapter(diffCallback, viewModel);
        adapter.setCallBack(this);
        newsList.setAdapter(adapter);
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.offline_articles);
        if (offlineDataShown) {
            item.setTitle("Go Online");
        } else {
            item.setTitle("Show Offline Articles");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.old_to_new:
                viewModel.sortOldToNew();
                return true;
            case R.id.new_to_old:
                viewModel.sortNewToOld();
                return true;
            case R.id.offline_articles:
                if (offlineDataShown) {
                    viewModel.fetchNewsData();
                    offlineDataShown = false;
                    invalidateOptionsMenu();
                } else {
                    fetchOfflineData();
                    offlineDataShown = true;
                    invalidateOptionsMenu();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteArticleFromDB(NewsData data) {
        DBTask task = new DBTask(dataSource, DBTask.Task.DATABASE_DELETE, data);
        viewModel.performDBOperation(task);
    }

    private void fetchOfflineData() {
        DBTask task = new DBTask(dataSource, DBTask.Task.DATABASE_FETCH_ALL, null);
        viewModel.performDBOperation(task);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public void onSaveArticleClicked(NewsData data) {
        DBTask task = new DBTask(dataSource, DBTask.Task.DATABASE_ADD, data);
        viewModel.performDBOperation(task);
    }
}
