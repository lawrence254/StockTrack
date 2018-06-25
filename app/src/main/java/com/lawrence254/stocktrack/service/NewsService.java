package com.lawrence254.stocktrack.service;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lawrence254.stocktrack.Constants;
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

    public ArrayList<News> processNews(Response response){
        ArrayList<News> news = new ArrayList<>();

        try {
            String json = response.body().string();

//            JSONArray jsonArray = new JSONArray(json);
//            StringBuilder sb = new StringBuilder();

//            for (int i = 0;i<jsonArray.length();i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String headline = jsonObject.getString("headline");
//                String source = jsonObject.getString("source");
//                String summary  = jsonObject.getString("summary");
//                String image_url = jsonObject.getString("image");
//
//
//                sb.insert(0,headline,source,summary,image_url);
//            }

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
}
