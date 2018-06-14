
package com.lawrence254.stocktrack.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StocksModel {

    @SerializedName("quote")
    @Expose
    private Quote quote;
    @SerializedName("news")
    @Expose
    private List<News> news = null;
    @SerializedName("chart")
    @Expose
    private List<Chart> chart = null;

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Chart> getChart() {
        return chart;
    }

    public void setChart(List<Chart> chart) {
        this.chart = chart;
    }

}
