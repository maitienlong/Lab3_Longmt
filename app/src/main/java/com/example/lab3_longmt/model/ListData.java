package com.example.lab3_longmt.model;

public class ListData {
    String title, date, content, excerpt, linkUrl;

    public ListData() {
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public ListData(String title, String date, String content, String excerpt, String linkUrl) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.excerpt = excerpt;
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
