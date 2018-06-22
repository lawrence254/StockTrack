package com.lawrence254.stocktrack.service;

import com.lawrence254.stocktrack.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EIXServiceInterfaces {

    @GET("/stock/{symbol}/batch")
    Call<List<Quote>> quote(@Path("symbol") String symbol);
}
