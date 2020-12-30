package com.sofiahardcode.newsapp;

public class Article {
    private String title;
    private String sectionName;
    private String date;
    private String author;
    private String url;

    public Article(String title, String sectionName, String date, String author, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.date = date;
        this.author = author;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }
}
