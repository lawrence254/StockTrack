package com.lawrence254.stocktrack.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapters.QuotesListAdapter;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.model.StocksModel;
import com.lawrence254.stocktrack.service.IEXService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
        public ArrayList<StocksModel> mStocks = new ArrayList<>();
    private QuotesListAdapter quotesListAdapter;
    @BindView(R.id.recycler)RecyclerView mRecycler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,root);

        getQuotes();
        return root;
    }

        private void getQuotes() {
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("StockTrack");
            progress.setMessage("Fetching Stocks...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        final IEXService iexService = new IEXService();

            progress.show();
            // To dismiss the dialog
        iexService.loadStocks(new Callback(){


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progress.dismiss();
                mStocks = iexService.processQuotes(response);
                getActivity().runOnUiThread(new Runnable() {


                    @Override
                    public void run() {
                        quotesListAdapter = new QuotesListAdapter(getContext(),mStocks);
                        mRecycler.setAdapter(quotesListAdapter);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecycler.setLayoutManager(layoutManager);
                        mRecycler.setHasFixedSize(true);
                    }
                });

            }
        });
}

}
