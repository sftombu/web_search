package com.tombu.web_search;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

public class ResultDocument {
    String title;
    String URL;
    String createdDate;
    String userId;

    public ResultDocument() {

    }

    public ResultDocument(String title, String URL, String createdDate, String userId) {
        this.title = title;
        this.URL = URL;
        this.createdDate = createdDate;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
