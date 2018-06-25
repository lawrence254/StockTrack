package com.lawrence254.stocktrack.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lawrence254.stocktrack.Constants;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.model.StocksModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IEXService {
    private static OkHttpClient client = new OkHttpClient();

    public static void loadStocks(Callback callback){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SYMBOLS,Constants.SYMBOLS_KEY);
        urlBuilder.addQueryParameter(Constants.TYPES,Constants.TYPES_KEY);
        urlBuilder.addQueryParameter(Constants.RANGE,Constants.RANGE_KEY);
        urlBuilder.addQueryParameter(Constants.LAST,Constants.LAST_KEY);
        String url = urlBuilder.build().toString();

        Log.d("URL", "created is: "+url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);

        call.enqueue(callback);

    }
    public ArrayList<StocksModel> processQuotes (Response response){
        ArrayList<StocksModel> quotes = new ArrayList<>();

        try {
            String json = response.body().string();
            Log.d("StocksModel", "response" + json);
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new GsonBuilder().create();

            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                Object key = it.next();

                Log.d("Iterator ", "Result: " + key.toString());
                StocksModel quote = gson.fromJson(jsonObject.getJSONObject(key.toString()).toString(), StocksModel.class);

                quotes.add(quote);

            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("CONTENT", ": "+quotes);
        return quotes;

    }
    public ArrayList<News> processNews(Response response){
        ArrayList<News> news = new ArrayList<>();
        try {
            String jObj = response.body().toString();
            JSONObject jsonObject = new JSONObject(jObj);
            Gson newsGson = new GsonBuilder().create();

            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                Object key = it.next();

                News nes = newsGson.fromJson(jsonObject.getJSONObject(key.toString()).getJSONArray("news").toString(), News.class);

                news.add(nes);
                Log.d("Result for ", "processNews: "+news);
            }

            } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }


}
