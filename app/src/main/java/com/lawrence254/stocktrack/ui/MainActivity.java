package com.lawrence254.stocktrack.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.QuotesListAdapter;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.service.IEXService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Quote> mQuote = new ArrayList<>();
    private QuotesListAdapter quotesListAdapter;
    @BindView(R.id.recycler)RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getQuotes();

    }

    private void getQuotes() {
        final IEXService iexService = new IEXService();

        iexService.loadStocks(new Callback(){


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mQuote = iexService.processQuotes(response);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        quotesListAdapter = new QuotesListAdapter(getApplicationContext(),mQuote);
                        mRecycler.setAdapter(quotesListAdapter);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        mRecycler.setLayoutManager(layoutManager);
                        mRecycler.setHasFixedSize(true);
                    }
                });

            }
        });
}
}
