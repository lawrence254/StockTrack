package com.lawrence254.stocktrack.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class StockDetailsActivity extends AppCompatActivity {

    @BindView(R.id.newsRecycle) RecyclerView mRecyclerView;
    private NewsCardsAdapter mAdapter;

    public ArrayList<News> mNews = new ArrayList<>();

    @BindView(R.id.graph)GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        ButterKnife.bind(this);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 15),
                new DataPoint(4, 33),
                new DataPoint(5, 1),
                new DataPoint(6, 3),
                new DataPoint(7, 6),
                new DataPoint(8, 17),
                new DataPoint(9, 25),
                new DataPoint(10, 0)
        });
        mGraph.addSeries(series);

        Intent intent = Objects.requireNonNull(StockDetailsActivity.this).getIntent();
        String symbol = intent.getStringExtra("symbol");
        if (symbol != null) {
            getNews(symbol);
        }
        else {
            Toast.makeText(getApplicationContext() ,"Symbol not retrieved correctly", Toast.LENGTH_SHORT).show();
        }
    }
    private void getNews(String symbol) {
        final ProgressDialog progress = new ProgressDialog(StockDetailsActivity.this);
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

                StockDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new NewsCardsAdapter(getApplicationContext(),mNews);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                        progress.dismiss();
                    }
                });

            }
        });
    }

}


