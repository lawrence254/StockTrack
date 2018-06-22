package com.lawrence254.stocktrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.lawrence254.stocktrack.R;
import com.lawrence254.stocktrack.adapter.QuotesListAdapter;
import com.lawrence254.stocktrack.model.Quote;
import com.lawrence254.stocktrack.service.EIXServiceInterfaces;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private QuotesListAdapter quotesListAdapter;
    @BindView(R.id.recycler)RecyclerView mRecycler;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this,root);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.iextrading.com/1.0")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        EIXServiceInterfaces eixServiceInterfaces = retrofit.create(EIXServiceInterfaces.class);
        Call<List<Quote>> call = eixServiceInterfaces.quote("aapl");

        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                List<Quote> quote = response.body();

                mRecycler.setAdapter(HomeFragment.this,quote);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecycler.setLayoutManager(layoutManager);
                mRecycler.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {

            }
        });


        return root;
    }

}
