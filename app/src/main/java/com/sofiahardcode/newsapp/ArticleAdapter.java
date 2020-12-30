package com.sofiahardcode.newsapp;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        Article article = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.article_title);
        titleTextView.setText(article.getTitle());

        TextView sectionNameTextView = convertView.findViewById(R.id.article_section_name);
        sectionNameTextView.setText(getContext().getString(R.string.category, article.getSectionName()));

        TextView authorTextView = convertView.findViewById(R.id.article_author);
        if (TextUtils.isEmpty(article.getAuthor())) {
            authorTextView.setVisibility(View.GONE);
        } else {
            authorTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(getContext().getString(R.string.written_by, article.getAuthor()));
        }

        TextView dateTextView = convertView.findViewById(R.id.article_date);
        if (TextUtils.isEmpty(article.getDate())) {
            dateTextView.setVisibility(View.GONE);
        } else {
            dateTextView.setVisibility(View.VISIBLE);
            dateTextView.setText(getContext().getString(R.string.published_at, formatDate(article.getDate())));
        }

        return convertView;
    }

    private String formatDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        String finalFormattedDate;

        try {
            Date convertedDate = dateFormat.parse(dateString);
            SimpleDateFormat formattedDate = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.ENGLISH);
            finalFormattedDate = formattedDate.format(convertedDate);
        } catch (ParseException e) {
            finalFormattedDate = dateString;
        }

        return finalFormattedDate;
    }
}
