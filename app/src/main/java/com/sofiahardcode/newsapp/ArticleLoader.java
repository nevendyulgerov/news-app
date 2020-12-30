package com.sofiahardcode.newsapp;

import android.content.Context;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private String url;

    public ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (url == null) {
            return null;
        }

        return Utils.fetchArticlesData(url);
    }
}
