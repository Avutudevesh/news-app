package com.example.news_app.db;

import android.os.AsyncTask;

import com.example.news_app.models.NewsData;

import java.util.ArrayList;
import java.util.List;
public class NewsDBRepository extends AsyncTask<DBTask, Integer, List<NewsData>> {

    public interface CallBack {
         void onFetchNewsDBData(List<NewsData> data);
    }

    private CallBack callBack = null;

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    @Override
    protected List<NewsData> doInBackground(DBTask... task) {
        List<NewsData> data = new ArrayList<>();
        for (int i = 0; i < task.length; i++) {
            DBTask currTask = task[i];
            switch (currTask.getTask()) {
                case DATABASE_ADD: {
                    currTask.getDataSource().insertNewsArticle(currTask.getData());
                    data = currTask.getDataSource().getAllNews();
                    break;
                }
                case DATABASE_DELETE: {
                    currTask.getDataSource().deleteNewsArticle(currTask.getData());
                    data = currTask.getDataSource().getAllNews();
                    break;
                }
                case DATABASE_FETCH_ALL: {
                    currTask.getDataSource().getAllNews();
                    data = currTask.getDataSource().getAllNews();
                    break;
                }

            }
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<NewsData> data) {
        callBack.onFetchNewsDBData(data);
    }
}
