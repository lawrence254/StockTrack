
package com.lawrence254.stocktrack.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FB {

    @SerializedName("quote")
    @Expose
    private Quote_ quote;
    @SerializedName("news")
    @Expose
    private List<News_> news = null;
    @SerializedName("chart")
    @Expose
    private List<Chart_> chart = null;

    public Quote_ getQuote() {
        return quote;
    }

    public void setQuote(Quote_ quote) {
        this.quote = quote;
    }

    public List<News_> getNews() {
        return news;
    }

    public void setNews(List<News_> news) {
        this.news = news;
    }

    public List<Chart_> getChart() {
        return chart;
    }

    public void setChart(List<Chart_> chart) {
        this.chart = chart;
    }

}
