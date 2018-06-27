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
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.NewsCardsAdapter;
import com.lawrence254.stocktrack.model.Chart;
import com.lawrence254.stocktrack.model.News;
import com.lawrence254.stocktrack.service.NewsService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public ArrayList<Chart> mChart = new ArrayList<>();
    public ArrayList<DataPoint> mDataPoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        ButterKnife.bind(this);

        Intent intent = Objects.requireNonNull(StockDetailsActivity.this).getIntent();
        String symbol = intent.getStringExtra("symbol");
        if (symbol != null) {
            getChart(symbol);
            getNews(symbol);
        }
        else {
            Toast.makeText(getApplicationContext() ,"Symbol not retrieved correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private void getChart(String symbol) {
        final NewsService newsService = new NewsService();
        newsService.getChart(symbol, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mChart = NewsService.processChart(response);

                for (int i =0;i<mChart.size();i++){
                    int high = mChart.get(i).getHigh().intValue();
                    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = sdf.parse(mChart.get(i).getDate());
                        Log.w("Date ", "onResponse: "+date );
//                        long ddate = (long) date/1000;

                        DataPoint dataPoint = new DataPoint(date, high);
                        mDataPoints.add(dataPoint);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                StockDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LineGraphSeries<DataPoint> series
                                = new LineGraphSeries<>(mDataPoints.toArray(new DataPoint[mDataPoints.size()]));

//                        mGraph.getViewport().setScalable(true);
//                        mGraph.getViewport().setScalableY(true);
                        mGraph.addSeries(series);
                        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(StockDetailsActivity.this));
//                        mGraph.getGridLabelRenderer().setHumanRounding(false);
                        }
                });
            }
        });
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


