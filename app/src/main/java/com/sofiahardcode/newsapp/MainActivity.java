package com.sofiahardcode.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    private static final String GUARDIAN_API_URL = "https://content.guardianapis.com/search";
    private static final int ARTICLE_LOADER_ID = 1;
    private List<Article> articles;
    private ArticleAdapter articleAdapter;
    private TextView emptyTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleAdapter = new ArticleAdapter(this, new ArrayList<>());
        ListView articlesListView = findViewById(R.id.article_list);
        articlesListView.setAdapter(articleAdapter);

        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articles.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        emptyTextView = findViewById(R.id.empty);
        articlesListView.setEmptyView(emptyTextView);

        progressBar = findViewById(R.id.spinner);
        if (isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        getSupportLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(GUARDIAN_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", "astronomy");
        uriBuilder.appendQueryParameter("api-key", getString(R.string.api_key));
        uriBuilder.appendQueryParameter("page-size", "20");
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        return new ArticleLoader(MainActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> articles) {
        emptyTextView.setText(isConnected() ? R.string.no_articles_found : R.string.no_internet_connection);
        progressBar.setVisibility(View.GONE);
        articleAdapter.clear();

        if (articles != null && !articles.isEmpty()) {
            this.articles = articles;
            articleAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        articleAdapter.clear();
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();
    }
}