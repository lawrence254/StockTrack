package com.lawrence254.stocktrack.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.NewsCardsAdapter;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockDetailsFragment extends Fragment {

    @BindView(R.id.newsRecycle) RecyclerView mRecyclerView;
    private NewsCardsAdapter mAdapter;

    public ArrayList<News> mNews = new ArrayList<>();

    @BindView(R.id.graph)GraphView mGraph;

    public StockDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_stock_details, container, false);
        ButterKnife.bind(this,root);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        mGraph.addSeries(series);

        String symbol = null;
//        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
//        String symbol = intent.getStringExtra("symbol");
        if (getArguments() != null) {
            symbol = this.getArguments().getString("symbol");
            Toast.makeText(getContext() ,"Symbol retrieved correctly -->"+symbol, Toast.LENGTH_SHORT).show();
            Log.d("Symbol", " Recieved: "+symbol);
        }
        else {
            Toast.makeText(getContext() ,"Symbol not retrieved correctly", Toast.LENGTH_SHORT).show();
        }


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

                        progress.dismiss();
                    }
                });

            }
        });
    }

}

