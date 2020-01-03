package com.technoidtintin.android.moviesmela;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Trending;
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

    private static final String TAG = Repository.class.getSimpleName();

    //Initializing ApiServices
    private ApiServices apiServices = ApiUtils.getApiServices();

    //Initialize Operator class
    private Operator operator = ApiUtils.getOperator();

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

    //Get Todays trend list
    public LiveData<Trending>getTrending(String type, String time, String apiKey){
        getTrendingList(type, time, apiKey);
        if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
            return trendTodayMutableLiveData;
        }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
           return trendWeekMutableLiveData;
        }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
            return trendMovieDayMutableLiveData;
        }else {
            return trendMovieDayMutableLiveData;
        }
    }

    //Get list of top rated Tv Shows
    public LiveData<List<ListItem>>getTopRatedTvShows(String path, String apiKey){
        getPopularTvShows(path,apiKey);
        return topRatedTvMutableLiveData;
    }

    //Get List of Popular Tv shows
    public LiveData<List<ListItem>>getPopularTvShows(String path, String apiKey){
        getPopularTvShowsList(path,apiKey);
        if (path.equals("popular")) {
            return popularTvItemsMutableLiveData;
        }else {
            return topRatedTvMutableLiveData;
        }
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
                            String image_url = "http://image.tmdb.org/t/p/w185" + posterImage;
                            String title = movieObj.getString("title");

                            ListItem listItem = new ListItem(id,title,image_url);
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
                            String image_url = "http://image.tmdb.org/t/p/w185" + posterImage;
                            String title = movieObj.getString("title");

                            ListItem listItem = new ListItem(id,title,image_url);
                            listItemList.add(listItem);
                        }
                        popularMovieListMutableLiveData.setValue(listItemList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Failed getting response: " + t.getMessage());
            }
        });
    }

    //Network call to get list of popular tv shows
    private void getPopularTvShowsList(final String path, String apiKey) {

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
                            String imageUrl = "http://image.tmdb.org/t/p/w185" + image;
                            ListItem listItem = new ListItem(id,name,imageUrl);
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
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Popular Tv Shows response failed: " + t.getMessage());
            }
        });
    }

    //Network call to get list of trending today
    private void getTrendingList(final String type, final String time, String apiKey) {

        operator.getTrending(type, time, apiKey).enqueue(new Callback<Trending>() {
            @Override
            public void onResponse(Call<Trending> call, Response<Trending> response) {

                Log.e(TAG,"Response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){
                    Log.e(TAG,"Trending response is successful: " + response.body().getTotalPages());

                    if (type.equals(Constant.ALL )&& time.equals(Constant.DAY)) {
                        trendTodayMutableLiveData.setValue(response.body());
                    }else if (type.equals(Constant.ALL )&& time.equals(Constant.WEEK)){
                            trendWeekMutableLiveData.setValue(response.body());
                    }else if (type.equals(Constant.MOVIE)&& time.equals(Constant.DAY)){
                        trendMovieDayMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Trending> call, Throwable t) {

                Log.e(TAG,"Trending response is failure: " + t.getMessage());
            }
        });
    }
}