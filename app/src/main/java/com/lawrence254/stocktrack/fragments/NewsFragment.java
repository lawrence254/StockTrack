package com.lawrence254.stocktrack.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.NewsAdapter;
import com.lawrence254.stocktrack.adapters.NewsCardsAdapter;
import com.lawrence254.stocktrack.model.Articles;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.service.IEXService;
import com.lawrence254.stocktrack.service.NewsService;
import com.lawrence254.stocktrack.ui.StockDetailsActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "News";
    ArrayList<String> selectedItems = new ArrayList<String>();
    String savedItems = "";

    @BindView(R.id.newsRecycler)
    RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    public ArrayList<Articles> mArticles = new ArrayList<>();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news, container, false);
    ButterKnife.bind(this,root);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(MyPREFERENCES)) {
            savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
            selectedItems.addAll(Arrays.asList(savedItems.split(",")));

        }
        getNews(savedItems);

        return root;
    }

    private void getNews(String savedItems) {
        final NewsService newsService = new NewsService();

        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("StockTrack");
        progress.setMessage("Fetching News From Your Sources...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        progress.show();

        newsService.getAllNews(savedItems, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mArticles = NewsService.processAllNews(response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new NewsAdapter(getContext(),mArticles);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                        progress.dismiss();
                    }
                });

            }
        });
    }

}

