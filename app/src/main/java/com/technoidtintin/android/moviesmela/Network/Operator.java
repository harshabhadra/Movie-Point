package com.technoidtintin.android.moviesmela.Network;

import com.technoidtintin.android.moviesmela.Model.MovieCredits;
import com.technoidtintin.android.moviesmela.Model.SimilarMovies;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.Model.SimilarTv;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.TvCredits;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.Model.TvShows;
import com.technoidtintin.android.moviesmela.TvVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Operator {

    @GET("/3/trending/{media_type}/{time_window}")
    Call<Trending> getTrending(@Path("media_type") String typePath,
                               @Path("time_window") String timePath,
                               @Query("api_key") String apiKey);

    @GET("/3/tv/{Path}")
    Call<TvShows>getTvOnAirList(@Path("Path")String path,
                                @Query("api_key")String apiKey);

    @GET("3/tv/{tv_id}")
    Call<TvDetails>getTvDetails(@Path("tv_id")int tv_id, @Query("api_key")String apiKey);

    @GET("3/movie/{movie_id}")
    Call<Movies>getMovieDetails(@Path("movie_id")int movie_id, @Query("api_key")String apiKey);

    @GET("3/tv/{tv_id}videos")
    Call<TvVideos>getTvVideos(@Path("tv_id")String tv_id,
                              @Query("api_key")String apiKey);

    @GET("3/tv/{tv_id}/similar?")
    Call<SimilarTv>getSimilarTvShows(@Path("tv_id")int tv_id,
                                     @Query("api_key")String apiKey);

    @GET("3/tv/{tv_id}/credits")
    Call<TvCredits>getTvCredits(@Path("tv_id")int tv_id,
                                @Query("api_key")String apiKey);

    @GET("3/movie/{movie_id}/credits")
    Call<MovieCredits>getMovieCredits(@Path("movie_id")int movie_id,
                                      @Query("api_key")String apiKey);

    @GET("3/movie/{movie_id}/similar")
    Call<SimilarMovies>getSimilarMovies(@Path("movie_id")int movie_id,
                                        @Query("api_key")String apiKey);
}
