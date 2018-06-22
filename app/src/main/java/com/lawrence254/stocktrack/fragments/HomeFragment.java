package com.lawrence254.stocktrack.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.lawrence254.stocktrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

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


        return root;
    }

}
