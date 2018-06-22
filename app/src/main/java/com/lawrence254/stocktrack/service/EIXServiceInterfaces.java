package com.lawrence254.stocktrack.service;

import retrofit2.Call;
import retrofit2.http.GET;

public class EIXServiceInterfaces {

    @GET("/stock/{symbol}/batch")
    Call<List<>>
}
