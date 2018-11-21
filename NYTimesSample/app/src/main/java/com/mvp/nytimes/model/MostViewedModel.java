package com.mvp.nytimes.model;

public class MostViewedModel {
    private String mSection;
    private String mAuthor;
    private String mPublishedDate;
    private String mTitle;

    public MostViewedModel(){
    }

    public MostViewedModel(String mSection, String mAuthor, String mPublishedDate, String mTitle) {
        this.mSection = mSection;
        this.mAuthor = mAuthor;
        this.mPublishedDate = mPublishedDate;
        this.mTitle = mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String mSection) {
        this.mSection = mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
