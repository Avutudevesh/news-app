package com.example.news_app.models;


import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class NewsData {
    private String id;
    private NewsSource source;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;

    public NewsData() {
        this.id = UUID.randomUUID().toString();
    }

    public NewsSource getSource() {
        return source;
    }

    public void setSource(NewsSource source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public static Comparator<NewsData> publishDateComparator = new Comparator<NewsData>() {
        @Override
        public int compare(NewsData newsData1, NewsData newsData2) {
            return newsData1.getPublishedAt().compareTo(newsData2.publishedAt);
        }
    };

    public static Comparator<NewsData> publishDateReverseComparator = new Comparator<NewsData>() {
        @Override
        public int compare(NewsData newsData1, NewsData newsData2) {
            return newsData2.getPublishedAt().compareTo(newsData1.publishedAt);
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}