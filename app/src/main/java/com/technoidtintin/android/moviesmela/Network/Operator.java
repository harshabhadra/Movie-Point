package com.technoidtintin.android.moviesmela.Network;

import com.technoidtintin.android.moviesmela.Model.Trending;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Operator {

    @GET("/3/trending/{media_type}/{time_window}")
    Call<Trending> getTrending(@Path("media_type") String typePath,
                               @Path("time_window") String timePath,
                               @Query("api_key") String apiKey);
}
