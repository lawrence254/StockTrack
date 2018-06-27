package com.lawrence254.stocktrack.service;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lawrence254.stocktrack.Constants;
import com.lawrence254.stocktrack.model.Chart;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.model.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsService {
    private static OkHttpClient client = new OkHttpClient();
    public static void findNews(String symbol, Callback callback) {
        HttpUrl.Builder builder = HttpUrl.parse(Constants.NEWS_URL).newBuilder();
        builder.addPathSegment(symbol);
        builder.addPathSegment(Constants.NEWS_KEY);
        builder.addPathSegment(Constants.LAST);
        builder.addPathSegment(Constants.LAST_KEY);

        String url = builder.build().toString();
        Log.d("URL FOR ", "findNews is: "+url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);

        call.enqueue(callback);

    }

    public static ArrayList<News> processNews(Response response){
        ArrayList<News> news = new ArrayList<>();

        try {
            String json = response.body().string();

            if (response.isSuccessful()){
                JSONArray quotesJson = new JSONArray(json);
                Type collectionType = new TypeToken<List<News>>() {}.getType();

                Gson gson = new GsonBuilder().create();

                news = gson.fromJson(quotesJson.toString(), collectionType);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }

    public static void getChart(String symbol, Callback callback) {
        HttpUrl.Builder builder = HttpUrl.parse(Constants.NEWS_URL).newBuilder();
        builder.addPathSegment(symbol);
        builder.addPathSegment(Constants.CHART_KEY);
        builder.addPathSegment(Constants.RANGE_KEY);

        String url = builder.build().toString();
        Log.d("URL FOR ", "findNews is: "+url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);

        call.enqueue(callback);

    }

    public static ArrayList<Chart> processChart(Response response){
        ArrayList<Chart> chart = new ArrayList<>();

        try {
            String json = response.body().string();

            if (response.isSuccessful()){
                JSONArray chartJson = new JSONArray(json);
                Type collectionType = new TypeToken<List<Chart>>() {}.getType();

                Gson gson = new GsonBuilder().create();

                chart = gson.fromJson(chartJson.toString(), collectionType);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chart;
    }


}
