package com.ceribit.android.asynctaskwithnewsapi.models;

public class Article {

    private String title;
    private String description;
    private String author;
    private String mContent;

    public Article(String title, String description, String author, String mContent) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.mContent = mContent;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }


}
