package com.lawrence254.stocktrack.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.service.IEXService;
import com.lawrence254.stocktrack.service.NewsService;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


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
    }

}
