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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.news_app.article.ArticleActivity;
import com.example.news_app.db.DBTask;
import com.example.news_app.db.NewsDataSource;
import com.example.news_app.models.NewsData;
import com.example.news_app.publisher.PublisherActivity;
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
    private ViewFlipper viewFlipper;
    private Button tryAgainButton;

    private enum Child {
        LOADING,
        LOADED,
        ERROR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new NewsDataSource(this);
        setContentView(R.layout.activity_main);
        viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
        tryAgainButton = findViewById(R.id.retry_button);
        setUpViewModel();
        setUpRecyclerView();
        setUpActionBar();
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
                offlineDataShown = false;
                invalidateOptionsMenu();
                viewModel.fetchNewsData();
            }
        });
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //Observer for online data changes
        final Observer<List<NewsData>> newsObserver = new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsData) {
                adapter.submitList(newsData);
                adapter.notifyDataSetChanged();
                viewFlipper.setDisplayedChild(Child.LOADED.ordinal());
            }
        };
        //Observer for offline data changes
        final Observer<List<NewsData>> offlineNewsObserver = new Observer<List<NewsData>>() {
            @Override
            public void onChanged(List<NewsData> newsData) {
                if (offlineDataShown) {
                    adapter.submitList(newsData);
                    adapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(Child.LOADED.ordinal());
                }
            }
        };
        //Observer for click action on news articles
        final Observer<String> clickObserver = new Observer<String>() {
            @Override
            public void onChanged(String url) {
                startActivity(ArticleActivity.getStartIntent(getApplicationContext(), url));
            }
        };
        //Observer to notify error while fetching data
        final Observer<Boolean> errorObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                viewFlipper.setDisplayedChild(Child.ERROR.ordinal());
            }
        };
        viewModel.getNewsDataLiveData().observe(this, newsObserver);
        viewModel.getDbNewsLiveData().observe(this, offlineNewsObserver);
        viewModel.getClickedNewsArticleLiveData().observe(this, clickObserver);
        viewModel.getErrorLiveData().observe(this, errorObserver);
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
                viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
                viewModel.sortOldToNew(offlineDataShown);
                return true;
            case R.id.new_to_old:
                viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
                viewModel.sortNewToOld(offlineDataShown);
                return true;
            case R.id.offline_articles:
                if (offlineDataShown) {
                    viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
                    viewModel.fetchNewsData();
                    offlineDataShown = false;
                    invalidateOptionsMenu();
                } else {
                    viewFlipper.setDisplayedChild(Child.LOADING.ordinal());
                    fetchOfflineData();
                    offlineDataShown = true;
                    invalidateOptionsMenu();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void deleteArticleFromDB(NewsData data) {
//        DBTask task = new DBTask(dataSource, DBTask.Task.DATABASE_DELETE, data);
//        viewModel.performDBOperation(task);
//    }

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

    //Callback action to save article to offline mode
    @Override
    public void onSaveArticleClicked(NewsData data) {
        DBTask task = new DBTask(dataSource, DBTask.Task.DATABASE_ADD, data);
        viewModel.performDBOperation(task);
        Toast.makeText(this, "Save to offline data.", Toast.LENGTH_LONG).show();
    }

    //Callback to filter articles based on publisher
    @Override
    public void onFilterFromPublisherClicked(NewsData data) {
        startActivity(PublisherActivity.getStartIntent(this, viewModel.getArticlesFromThisPublisher(data), data.getSource().getName()));
    }
}
