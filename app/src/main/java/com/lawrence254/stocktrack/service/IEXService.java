package com.lawrence254.stocktrack.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lawrence254.stocktrack.Constants;
import com.lawrence254.stocktrack.model.Quote;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        urlBuilder.addPathSegment(Constants.SYMBOLS);
        urlBuilder.addPathSegment(Constants.TYPES);
        urlBuilder.addPathSegment(Constants.RANGE);
        urlBuilder.addPathSegment(Constants.LAST);

        String url = urlBuilder.build().toString();

        Log.d("URL", "created is: "+url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);

        call.enqueue(callback);

    }
    public ArrayList<Quote> processQuotes (Response response){
        ArrayList<Quote> quotes = new ArrayList<>();

        try {
            String json = response.body().toString();

            if (response.isSuccessful()){
                JSONObject quotesJson = new JSONObject(json);
                Type collectionType = new TypeToken<List<Quote>>() {}.getType();
                Gson gson = new GsonBuilder().create();

                quotes = gson.fromJson(quotesJson.toString(), collectionType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quotes;
    }

}
