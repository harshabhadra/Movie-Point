package com.technoidtintin.android.moviesmela.Network;

import com.technoidtintin.android.moviesmela.Model.Trending;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Operator {

    @GET("/3/trending/all/day")
    Call<Trending> getTrendingToday(@Query("api_key") String apiKey);
}
