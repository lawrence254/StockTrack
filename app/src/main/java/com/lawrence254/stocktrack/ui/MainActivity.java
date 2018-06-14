package com.lawrence254.stocktrack.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.lawrence254.stocktrack.DB.DBHelper;
import com.lawrence254.stocktrack.R;
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
}
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

        iexService.processQuotes(new CallBack(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mQuote = IEXService.loadStocks(response);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        });
    }
}
