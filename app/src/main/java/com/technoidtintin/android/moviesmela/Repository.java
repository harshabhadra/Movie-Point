package com.technoidtintin.android.moviesmela;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.technoidtintin.android.moviesmela.Dao.FavMovieDao;
import com.technoidtintin.android.moviesmela.Dao.FavTvShowsDao;
import com.technoidtintin.android.moviesmela.Database.MoviePointDatabase;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.MovieCredits;
import com.technoidtintin.android.moviesmela.Model.SimilarMovies;
import com.technoidtintin.android.moviesmela.Model.SimilarTv;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.Model.TvShows;
import com.technoidtintin.android.moviesmela.Network.ApiServices;
import com.technoidtintin.android.moviesmela.Network.ApiUtils;
import com.technoidtintin.android.moviesmela.Network.Operator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {


    public static Repository getInstance(){
        return new Repository();
    }

    public Repository() {
    }

    private FavTvShowsDao favTvShowsDao;
    private FavMovieDao favMovieDao;

    public Repository(Application application){
        MoviePointDatabase moviePointDatabase = MoviePointDatabase.getInstance(application);
        favMovieDao = moviePointDatabase.favMovieDao();
        favTvShowsDao = moviePointDatabase.favTvShowsDao();
    }


    private static final String TAG = Repository.class.getSimpleName();

    //Initializing ApiServices
    private ApiServices apiServices = ApiUtils.getApiServices();

    //Initialize Operator class
    private Operator operator = ApiUtils.getOperator();

    //Store Fav Movie List
    private LiveData<List<Movies>>favMoviesListMutableLiveData = new MutableLiveData<>();

    //Store Fav Tv Shows list
    private LiveData<List<TvDetails>>favTvShowsMutableLiveData = new MutableLiveData<>();

    //Store list of MovieItems
    private MutableLiveData<List<ListItem>> popularMovieListMutableLiveData = new MutableLiveData<>();

    //Store list of top rated movies
    private MutableLiveData<List<ListItem>>topratedMovieListMutableLiveData = new MutableLiveData<>();

    //Store list of Popular TvShows
    private MutableLiveData<List<ListItem>>popularTvItemsMutableLiveData = new MutableLiveData<>();

    //Store list of Top rated tv shows
    private MutableLiveData<List<ListItem>>topRatedTvMutableLiveData = new MutableLiveData<>();

    //Store list of trends today
    private MutableLiveData<Trending>trendTodayMutableLiveData = new MutableLiveData<>();

    //Store list of trending this week
    private MutableLiveData<Trending>trendWeekMutableLiveData = new MutableLiveData<>();

    //Store list of Trending Movie list of the day
    private MutableLiveData<Trending>trendMovieDayMutableLiveData = new MutableLiveData<>();

    //Store list of Movies Now Playing
    private MutableLiveData<List<ListItem>>nowPlayingMovieMutableLiveData = new MutableLiveData<>();

    //Store list of Tv Shows on air
    private MutableLiveData<TvShows>tvShowsOnAirMutableLiveData = new MutableLiveData<>();

    //Store List of Tv Shows on Air Today
    private MutableLiveData<TvShows>tvShowsTodayMutableLiveData = new MutableLiveData<>();

    //Store list of popular tv shows
    private MutableLiveData<TvShows>tvShowsPopularMutableLiveData = new MutableLiveData<>();

    //Store list of Top Rated Tv Shows
    private MutableLiveData<TvShows>tvShowsTopRatedMutableLiveData = new MutableLiveData<>();

    //Store list of trending Tv Shows of the day
    private MutableLiveData<Trending>trendTvDayMutableLiveData = new MutableLiveData<>();

    //Store list of Trending Tv shows of this week
    private MutableLiveData<Trending>trendTvWeekMutableLiveData = new MutableLiveData<>();

    //Store Movie Details
    private MutableLiveData<Movies>movieDetailsMutableLiveData = new MutableLiveData<>();

    //Store Tv Details
    private MutableLiveData<TvDetails>tvDetailsMutableLiveData = new MutableLiveData<>();

    //Store TvVideos
    private MutableLiveData<TvVideos>tvVideosMutableLiveData = new MutableLiveData<>();

    //Store similar TvShows data
    private MutableLiveData<SimilarTv>similarTvMutableLiveData = new MutableLiveData<>();

    //Store Tv Credits
    private MutableLiveData<TvCredits>tvCreditsMutableLiveData = new MutableLiveData<>();

    //Store Movie Credits
    private MutableLiveData<MovieCredits>movieCreditsMutableLiveData = new MutableLiveData<>();

    //Store Similar Movies
    private MutableLiveData<SimilarMovies>similarMoviesMutableLiveData = new MutableLiveData<>();

    //Store Reviews of Movies
    private MutableLiveData<ReViewsList>reViewsListMutableLiveData = new MutableLiveData<>();

    //Store Season Details
    private MutableLiveData<SeasonDetails>seasonDetailsMutableLiveData = new MutableLiveData<>();

    //Get Fav movies
    public LiveData<List<Movies>>getFavMoives(){
        favMoviesListMutableLiveData = favMovieDao.getFavMovieList();
        return favMoviesListMutableLiveData;
    }

    //Get Fav Tv Shows List
    public LiveData<List<TvDetails>>getFavTvShowsList(){
        favTvShowsMutableLiveData = favTvShowsDao.getFavTvShows();
        return favTvShowsMutableLiveData;
    }

    //Insert Movie to Fav list
    public void insertMoviesToFav(Movies movies){

        new insertFavMoviesAsync(favMovieDao).execute(movies);
    }

    //Insert Tv Show to fav list
    public void insertTvShowsToFav(TvDetails tvDetails){
        new insertTvshowsAsync(favTvShowsDao).execute(tvDetails);
    }

    //delete movie from Fav list
    public void deleteMovieFromFav(Movies movies){
        new deleteMovieAsync(favMovieDao).execute(movies);
    }

    //Delete tv Show from fav list
    public void deleteTvShowFromFav(TvDetails tvDetails){
        new delelteTvShowAsync(favTvShowsDao).execute(tvDetails);
    }



    //Get SeasonDetails
    public LiveData<SeasonDetails>getSeasonDetailsLiveData(int tv_id, int season_number, String apiKey){

        getSeasonDetails(tv_id,season_number,apiKey);
        return seasonDetailsMutableLiveData;
    }

    //Get Movie Reviews
    public LiveData<ReViewsList>getMovieReviewList(int movieId, String apiKey){
        getMovieReview(movieId,apiKey);
        return reViewsListMutableLiveData;
    }

    //Get Similar Movies
    public LiveData<SimilarMovies>getSimilarMovies(int movieId, String apiKey){
        getSimilarMovieList(movieId,apiKey);
        return similarMoviesMutableLiveData;
    }

    //Get Movie Credits
    public LiveData<MovieCredits>getMovieCredits(int id, String apiKey){
        getMovieCast(id,apiKey);
        return movieCreditsMutableLiveData;
    }

    //Get Tv Credits
    public LiveData<TvCredits>getTvCredits(int tv_id, String apiKey){
        getTvCreditsLiveData(tv_id,apiKey);
        return tvCreditsMutableLiveData;
    }

    //Get Similar Tv Shows
    public LiveData<SimilarTv>getSimilarTvShows(int tvId, String apiKey){
        getSimilarTvShowsList(tvId,apiKey);
        return similarTvMutableLiveData;
    }

    //Get TvVideos
    public LiveData<TvVideos>getTvVideosList(String tv_id, String apiKey){
        getTvVideos(tv_id,apiKey);
        return tvVideosMutableLiveData;
    }

    //Get Movie Details
    public LiveData<Movies>getMovieDetails(int movieId, String apiKey){
        getDetialsMovie(movieId,apiKey);
        return movieDetailsMutableLiveData;
    }

    //Get Tv Details
    public LiveData<TvDetails>getTvDetails(int tvId, String apiKey){
        getDetailsTv(tvId,apiKey);
        return tvDetailsMutableLiveData;
    }

    //Get List of Tv Shows on Air
    public LiveData<TvShows>getTvShowsOnAir(String path, String apiKey){

        getTvShowsOnAirList(path,apiKey);
        if (path.equals(Constant.ON_THE_AIR)) {
            return tvShowsOnAirMutableLiveData;
        }else if (path.equals(Constant.AIR_TODYA)){
            return tvShowsTodayMutableLiveData;
        }else if (path.equals(Constant.POPULAR)){
            return tvShowsPopularMutableLiveData;
        }else {
            return tvShowsTopRatedMutableLiveData;
        }
    }

    //Get Todays trend list
    public LiveData<Trending>getTrending(String type, String time, String apiKey){
        getTrendingList(type, time, apiKey);
        if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
            return trendTodayMutableLiveData;
        }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
           return trendWeekMutableLiveData;
        }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
            return trendMovieDayMutableLiveData;
        }else if (type.equals(Constant.TV)&& time.equals(Constant.DAY)){
            return trendTvDayMutableLiveData;
        }else {
            return trendTvWeekMutableLiveData;
        }
    }

    //Get List of Popular Tv shows
    public LiveData<List<ListItem>> getTvShows(String path, String apiKey){
        getTvShowsList(path,apiKey);
        if (path.equals("popular")) {
            return popularTvItemsMutableLiveData;
        }else {
            return topRatedTvMutableLiveData;
        }
    }

    //Get List of Now Playing Movies
    public LiveData<List<ListItem>>getNowPlayingMovies(String apiKey){
        getNowPlayingMovieList(apiKey);
        return nowPlayingMovieMutableLiveData;
    }

    //Get List of MovieItems
    public LiveData<List<ListItem>> getPopularMovies(String apiKey){

        getPopularMoviesList(apiKey);
        return popularMovieListMutableLiveData;
    }

    //Get Top rated movies
    public LiveData<List<ListItem>>getTopRatedMovies(String apiKey){
        getTopRatedMovieList(apiKey);
        return topratedMovieListMutableLiveData;
    }

    //Network call to get top rated movie list
    private void getTopRatedMovieList(String apiKey) {
        final List<ListItem> listItemList = new ArrayList<>();

        apiServices.getMovies("top_rated",apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"Response is successful:  " + response.body() );
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        JSONArray jsonArray = object.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject movieObj = jsonArray.getJSONObject(i);

                            int id = movieObj.getInt("id");
                            String posterImage = movieObj.getString("poster_path");
                            String image_url = "http://image.tmdb.org/t/p/w300" + posterImage;
                            String title = movieObj.getString("title");

                            ListItem listItem = new ListItem(id,Constant.MOVIE_TYPE,title,image_url);
                            listItemList.add(listItem);
                        }
                        topratedMovieListMutableLiveData.setValue(listItemList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Failed getting response: " + t.getMessage());
                topratedMovieListMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Movies list
    private void getPopularMoviesList(String apiKey) {

        final List<ListItem> listItemList = new ArrayList<>();

        apiServices.getMovies("popular",apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"Response is successful " );
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        JSONArray jsonArray = object.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject movieObj = jsonArray.getJSONObject(i);

                            int id = movieObj.getInt("id");
                            String posterImage = movieObj.getString("poster_path");
                            String image_url = "http://image.tmdb.org/t/p/w342" + posterImage;
                            String title = movieObj.getString("title");

                            ListItem listItem = new ListItem(id,Constant.MOVIE_TYPE,title,image_url);
                            listItemList.add(listItem);
                        }
                        popularMovieListMutableLiveData.setValue(listItemList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    popularMovieListMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Failed getting response: " + t.getMessage());
                popularMovieListMutableLiveData.setValue(null);
            }
        });
    }

    //Network request to get Now Playing Movies List
    private void getNowPlayingMovieList(String apiKey) {

        final List<ListItem> listItemList = new ArrayList<>();

        apiServices.getMovies("now_playing",apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"Response is successful " );
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        JSONArray jsonArray = object.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject movieObj = jsonArray.getJSONObject(i);

                            int id = movieObj.getInt("id");
                            String posterImage = movieObj.getString("poster_path");
                            String image_url = "http://image.tmdb.org/t/p/w342" + posterImage;
                            String title = movieObj.getString("title");

                            ListItem listItem = new ListItem(id,Constant.MOVIE_TYPE,title,image_url);
                            listItemList.add(listItem);
                        }
                        nowPlayingMovieMutableLiveData.setValue(listItemList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    nowPlayingMovieMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Failed getting response: " + t.getMessage());
                nowPlayingMovieMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get list of popular tv shows
    private void getTvShowsList(final String path, String apiKey) {

        final List<ListItem>popularTvshowsList = new ArrayList<>();
        apiServices.getTVShowsList(path,apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"Tv Shows response is: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray resultsArray = jsonObject.getJSONArray("results");

                        for (int i = 0; i<resultsArray.length(); i++){

                            JSONObject object = resultsArray.getJSONObject(i);
                            int id = object.optInt("id");
                            String name = object.optString("original_name");
                            String image = object.optString("poster_path");
                            String imageUrl = "http://image.tmdb.org/t/p/w342" + image;
                            ListItem listItem = new ListItem(id,Constant.TV_TYPE,name,imageUrl);
                            popularTvshowsList.add(listItem);
                        }
                        if (path.equals("popular")) {
                            popularTvItemsMutableLiveData.setValue(popularTvshowsList);
                        }else {
                            topRatedTvMutableLiveData.setValue(popularTvshowsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    if (path.equals("popular")) {
                        popularTvItemsMutableLiveData.setValue(null);
                    }else {
                        topRatedTvMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Popular Tv Shows response failed: " + t.getMessage());
                if (path.equals("popular")) {
                    popularTvItemsMutableLiveData.setValue(null);
                }else {
                    topRatedTvMutableLiveData.setValue(null);
                }
            }
        });
    }

    //Network call to get list of trending today
    private void getTrendingList(final String type, final String time, String apiKey) {

        operator.getTrending(type, time, apiKey).enqueue(new Callback<Trending>() {
            @Override
            public void onResponse(Call<Trending> call, Response<Trending> response) {

                Log.e(TAG,"Trending Response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){
                    Log.e(TAG,"Trending response is successful: " + response.body().getTotalPages());

                    if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
                        trendTodayMutableLiveData.setValue(response.body());
                    }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
                            trendWeekMutableLiveData.setValue(response.body());
                    }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
                        trendMovieDayMutableLiveData.setValue(response.body());
                    }else if (type.equals(Constant.TV)&& time.equals(Constant.DAY)){
                        trendTvDayMutableLiveData.setValue(response.body());
                    }else {
                        trendTvWeekMutableLiveData.setValue(response.body());
                    }
                }else {
                    if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
                        trendTodayMutableLiveData.setValue(null);
                    }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
                        trendWeekMutableLiveData.setValue(null);
                    }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
                        trendMovieDayMutableLiveData.setValue(null);
                    }else if (type.equals(Constant.TV)&& time.equals(Constant.DAY)){
                        trendTvDayMutableLiveData.setValue(null);
                    }else {
                        trendTvWeekMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<Trending> call, Throwable t) {

                Log.e(TAG,"Trending response is failure: " + t.getMessage());
                if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
                    trendTodayMutableLiveData.setValue(null);
                }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
                    trendWeekMutableLiveData.setValue(null);
                }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
                    trendMovieDayMutableLiveData.setValue(null);
                }else if (type.equals(Constant.TV)&& time.equals(Constant.DAY)){
                    trendTvDayMutableLiveData.setValue(null);
                }else {
                    trendTvWeekMutableLiveData.setValue(null);
                }
            }
        });
    }

    //Network call to get list of Tv Shows on Air
    private void getTvShowsOnAirList(final String path, String apiKey) {

        operator.getTvOnAirList(path,apiKey).enqueue(new Callback<TvShows>() {
            @Override
            public void onResponse(Call<TvShows> call, Response<TvShows> response) {
                Log.e(TAG,"Tv Shows on Air response is: " + response.body());
                if (response.body()!= null && response.isSuccessful()){
                    Log.e(TAG,"Tv Shows response suceessfull");
                    if (path.equals(Constant.ON_THE_AIR)){
                        tvShowsOnAirMutableLiveData.setValue(response.body());
                    }else if (path.equals(Constant.AIR_TODYA)){
                        tvShowsTodayMutableLiveData.setValue(response.body());
                    }else if (path.equals(Constant.POPULAR)){
                        tvShowsPopularMutableLiveData.setValue(response.body());
                    }else if (path.equals(Constant.TOP_RATED)){
                        tvShowsTopRatedMutableLiveData.setValue(response.body());
                    }
                }else {
                    if (path.equals(Constant.ON_THE_AIR)){
                        tvShowsOnAirMutableLiveData.setValue(null);
                    }else if (path.equals(Constant.AIR_TODYA)){
                        tvShowsTodayMutableLiveData.setValue(null);
                    }else if (path.equals(Constant.POPULAR)){
                        tvShowsPopularMutableLiveData.setValue(null);
                    }else if (path.equals(Constant.TOP_RATED)){
                        tvShowsTopRatedMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<TvShows> call, Throwable t) {

                Log.e(TAG,"Tv Shows on air response failure: " + t.getMessage());
                if (path.equals(Constant.ON_THE_AIR)){
                    tvShowsOnAirMutableLiveData.setValue(null);
                }else if (path.equals(Constant.AIR_TODYA)){
                    tvShowsTodayMutableLiveData.setValue(null);
                }else if (path.equals(Constant.POPULAR)){
                    tvShowsPopularMutableLiveData.setValue(null);
                }else if (path.equals(Constant.TOP_RATED)){
                    tvShowsTopRatedMutableLiveData.setValue(null);
                }
            }
        });
    }

    //Network call to get Tv Shows Details
    private void getDetailsTv(int tvId, String apiKey) {

        operator.getTvDetails(tvId,apiKey).enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(Call<TvDetails> call, Response<TvDetails> response) {
                Log.e(TAG,"TV details response: " + response.body());
                if (response.isSuccessful() && response.body()!= null){
                    Log.e(TAG,"Tv Shows details response is successful");
                    tvDetailsMutableLiveData.setValue(response.body());
                }else {
                    tvDetailsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TvDetails> call, Throwable t) {

                Log.e(TAG,"Tv shows details response is failure: " + t.getMessage());
                tvDetailsMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Movies details
    private void getDetialsMovie(int movieId, String apiKey) {

        operator.getMovieDetails(movieId,apiKey).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.isSuccessful()&& response.body() != null){
                    Log.e(TAG, "Movie details response is successful");
                    movieDetailsMutableLiveData.setValue(response.body());
                }else {
                    movieDetailsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

                Log.e(TAG,"Movie details response is failure: " + t.getMessage());
                movieDetailsMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Tv Videos
    private void getTvVideos(String tv_id, String apiKey) {

        operator.getTvVideos(tv_id,apiKey).enqueue(new Callback<TvVideos>() {
            @Override
            public void onResponse(Call<TvVideos> call, Response<TvVideos> response) {
                Log.e(TAG,"Tv Videos response : " + response.body());
                if (response.body() != null && response.isSuccessful()){
                    Log.e(TAG, "Tv Videos response is successful");
                    tvVideosMutableLiveData.setValue(response.body());
                }else {
                    tvVideosMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TvVideos> call, Throwable t) {

                Log.e(TAG,"Tv Videos response is failed: " + t.getMessage());
                tvVideosMutableLiveData.setValue(null);
            }
        });
    }

    //Network reqeuest to get Similar Tv Shows
    private void getSimilarTvShowsList(int tvId, String apiKey) {

        operator.getSimilarTvShows(tvId,apiKey).enqueue(new Callback<SimilarTv>() {
            @Override
            public void onResponse(Call<SimilarTv> call, Response<SimilarTv> response) {

                Log.e(TAG,"Similar Tv Shows response : " + response.body());
                if (response.body() != null && response.isSuccessful()) {
                    Log.e(TAG,"Similar Tv Shows response is successful: "
                            + response.body().getPage() + ", " + response.body().getTotalResults());
                    similarTvMutableLiveData.setValue(response.body());
                }else {
                    similarTvMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SimilarTv> call, Throwable t) {

                Log.e(TAG,"Similar Tv Shows response is failure: "  + t.getMessage());
                similarTvMutableLiveData.setValue(null);
            }
        });
    }

    //Network request to get Tv Credits
    private void getTvCreditsLiveData(int tv_id, String apiKey) {

        operator.getTvCredits(tv_id,apiKey).enqueue(new Callback<TvCredits>() {
            @Override
            public void onResponse(Call<TvCredits> call, Response<TvCredits> response) {

                if (response.isSuccessful()&& response.body() != null){
                    Log.e(TAG,"Tv Credit response is successful");
                    tvCreditsMutableLiveData.setValue(response.body());
                }else {
                    tvCreditsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TvCredits> call, Throwable t) {

                Log.e(TAG,"Tv Credits Resposne is failure: " + t.getMessage());
                tvCreditsMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Movie Cast
    private void getMovieCast(int id, String apiKey) {

        operator.getMovieCredits(id, apiKey).enqueue(new Callback<MovieCredits>() {
            @Override
            public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                Log.e(TAG,"Movie credit response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){
                    Log.e(TAG,"Movie Credit response is successful: " + response.body().getId());
                    movieCreditsMutableLiveData.setValue(response.body());
                }else {
                    movieCreditsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieCredits> call, Throwable t) {

                Log.e(TAG,"Movie credit response is failure: " + t.getMessage());
                movieCreditsMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Similar Movies
    private void getSimilarMovieList(int movieId, String apiKey) {

        operator.getSimilarMovies(movieId,apiKey).enqueue(new Callback<SimilarMovies>() {
            @Override
            public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {

                Log.e(TAG,"Similar Movies Response is : " + response.body());
                if (response.body() != null && response.isSuccessful()){
                    Log.e(TAG,"Similar Movies response is successful: " + response.body().totalPages);
                    similarMoviesMutableLiveData.setValue(response.body());
                }else {
                    similarMoviesMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SimilarMovies> call, Throwable t) {

                Log.e(TAG,"Similar movie response is failure: " + t.getMessage());
                similarMoviesMutableLiveData.setValue(null);
            }
        });
    }

    //Network call to get Movie review list
    private void getMovieReview(int movieId, String apiKey) {

        operator.getMovieReViews(movieId, apiKey).enqueue(new Callback<ReViewsList>() {
            @Override
            public void onResponse(Call<ReViewsList> call, Response<ReViewsList> response) {

                Log.e(TAG,"Movie review response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){
                    Log.e(TAG,"Movie review response is successful: " + response.body().getTotalPages());
                    reViewsListMutableLiveData.setValue(response.body());
                }else{
                    reViewsListMutableLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ReViewsList> call, Throwable t) {

                Log.e(TAG,"Movie review response is failure : " + t.getMessage());
                reViewsListMutableLiveData.setValue(null);
            }
        });
    }

    //Network Call To get Season Details
    private void getSeasonDetails(int tv_id, int season_number, String apiKey) {

        operator.getSeasonDetails(tv_id,season_number,apiKey).enqueue(new Callback<SeasonDetails>() {
            @Override
            public void onResponse(Call<SeasonDetails> call, Response<SeasonDetails> response) {
                Log.e(TAG,"Season details response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){
                    seasonDetailsMutableLiveData.setValue(response.body());
                }else {
                    seasonDetailsMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SeasonDetails> call, Throwable t) {

                Log.e(TAG,"Season Details response is failure: " + t.getMessage());
                seasonDetailsMutableLiveData.setValue(null);
            }
        });
    }

    //AsyncTask to insert movies to Fav list
    private static class insertFavMoviesAsync extends AsyncTask<Movies,Void,Void>{

        FavMovieDao favMovieDao;

        public insertFavMoviesAsync(FavMovieDao favMovieDao) {
            this.favMovieDao = favMovieDao;
        }

        @Override
        protected Void doInBackground(Movies... movies) {

            favMovieDao.insertFavMovie(movies[0]);
            return null;
        }
    }

    //AsyncTask to insert Tv shows to favorite list
    private static class insertTvshowsAsync extends AsyncTask<TvDetails, Void, Void>{

        FavTvShowsDao favTvShowsDao;

        public insertTvshowsAsync(FavTvShowsDao favTvShowsDao) {
            this.favTvShowsDao = favTvShowsDao;
        }

        @Override
        protected Void doInBackground(TvDetails... tvDetails) {

            favTvShowsDao.insertFavTvShow(tvDetails[0]);
            return null;
        }
    }

    //AsyncTask to Delete a movie from Fav list
    private static class deleteMovieAsync extends AsyncTask<Movies, Void , Void>{

        FavMovieDao favMovieDao;

        public deleteMovieAsync(FavMovieDao favMovieDao) {
            this.favMovieDao = favMovieDao;
        }

        @Override
        protected Void doInBackground(Movies... movies) {

            favMovieDao.deleteFavMovie(movies[0]);
            return null;
        }
    }

    //AsyncTask to delete TvShow from fav list
    private static class delelteTvShowAsync extends AsyncTask<TvDetails, Void, Void>{

        FavTvShowsDao favTvShowsDao;

        public delelteTvShowAsync(FavTvShowsDao favTvShowsDao) {
            this.favTvShowsDao = favTvShowsDao;
        }

        @Override
        protected Void doInBackground(TvDetails... tvDetails) {

            favTvShowsDao.delteFavTvShow(tvDetails[0]);
            return null;
        }
    }
}
