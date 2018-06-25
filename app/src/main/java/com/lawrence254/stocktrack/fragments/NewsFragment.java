package com.lawrence254.stocktrack.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.NewsCardsAdapter;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.service.IEXService;
import com.lawrence254.stocktrack.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    @BindView(R.id.newsRecycle) RecyclerView mRecyclerView;
    private NewsCardsAdapter mAdapter;

    public ArrayList<News> mNews = new ArrayList<>();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_news, container, false);

        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        String symbol = intent.getStringExtra("symbol");

       getNews(symbol);
        return root;
    }

    private void getNews(String symbol) {
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("StockTrack");
        progress.setMessage("Fetching News...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        final NewsService newsService = new NewsService();

        progress.show();

        newsService.findNews(symbol, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mNews = NewsService.processNews(response);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new NewsCardsAdapter(getContext(),mNews);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
                        }
                    });

            }
        });
    }

}
