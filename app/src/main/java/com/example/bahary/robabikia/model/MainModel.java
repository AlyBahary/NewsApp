package com.example.bahary.robabikia.model;

import android.widget.LinearLayout;

import java.util.List;

public class MainModel

{
    private List<Articles> articles;

    private String totalResults;

    private String status;

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public String getTotalResults ()
    {
        return totalResults;
    }

    public void setTotalResults (String totalResults)
    {
        this.totalResults = totalResults;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [articles = "+articles+", totalResults = "+totalResults+", status = "+status+"]";
    }
}
