package com.technoidtintin.android.moviesmela.ui.Movies;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.HomeItem;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentMovieBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMovieBinding fragmentMovieBinding;
    private MovieSliderAdapter sliderAdapter;
    private MovieAdapter movieAdapter;

    private List<HomeItem>homeItemList = new ArrayList<>();

    private static final String TAG = MovieFragment.class.getSimpleName();
    private String apiKey;

    private AlertDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        apiKey = getResources().getString(R.string.api_key);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        //Initializing DataBinding
        fragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);

        //Create Loading Dialog
        loadingDialog = createAlertDialgo(getContext());
        loadingDialog.show();

        //Initializing Slider Adapter
        sliderAdapter = new MovieSliderAdapter(getContext());
        fragmentMovieBinding.movieSliderView.setSliderAdapter(sliderAdapter);
        fragmentMovieBinding.movieSliderView.setScrollTimeInSec(6);
        getTrendingMoviesToday();

        fragmentMovieBinding.movieRecycler.setHasFixedSize(true);
        fragmentMovieBinding.movieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getContext());
        fragmentMovieBinding.movieRecycler.setAdapter(movieAdapter);
        getTrendingMoviesWeek();

        return fragmentMovieBinding.getRoot();
    }

    //Get list of Trending Today
    private void getTrendingMoviesToday() {

        moviesViewModel.getTrendingMovies(Constant.MOVIE, Constant.DAY,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null){
                    Log.e(TAG,"Movie Trending list of day is full");
                    List<TrendResult>trendResultList = trending.getResults();
                    sliderAdapter.setTrendResultList(trendResultList);
                }else {
                    Log.e(TAG,"Trending Movie list is empty");
                }
            }
        });
    }

    //Get Trending Movies this week
    private void getTrendingMoviesWeek(){
        moviesViewModel.getTrendingMovies(Constant.MOVIE,Constant.WEEK,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null){
                    Log.e(TAG,"Movie trending week list is null");
                    List<TrendResult>trendResultList = trending.getResults();
                    List<ListItem>movieList = getTrendMovies(trendResultList);
                    if (movieList != null){
                        Log.e(TAG,"Movie trending week list is null");
                        homeItemList.add(new HomeItem("Trending This Week",movieList));
                        if (homeItemList != null){
                            getNowPlayingMovies();
                        }
                    }
                }else {
                    getNowPlayingMovies();
                }

            }
        });
    }

    //Get Now Playing MovieList
    private void getNowPlayingMovies(){
        moviesViewModel.getNowPlayingMovies(apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null){
                    homeItemList.add(new HomeItem("Now Playing",listItems));
                    if (homeItemList != null){
                        getPopularMovieList();
                    }
                }else {
                    getPopularMovieList();
                }
            }
        });
    }

    //Get Popular Movies
    private void getPopularMovieList() {

        moviesViewModel.getPopularMovies(apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItemList) {
                if (listItemList != null) {

                    homeItemList.add(new HomeItem("Popular Movies", listItemList));
                    if (homeItemList != null) {
                        getTopratedMoveis();
                    }
                } else {
                    Log.e(TAG, "Movie list is null");
                    getTopratedMoveis();
                }
            }
        });
    }

    //Get top rated movies
    private void getTopratedMoveis() {
        moviesViewModel.getTopRatedMovies(apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null) {
                    Log.e(TAG, "Top movies list is full");
                    homeItemList.add(new HomeItem("Top Rated Movies", listItems));
                    if (homeItemList != null) {
                        loadingDialog.dismiss();
                        addItemToHomeList(homeItemList);
                    }
                } else {
                    if (homeItemList != null){
                        addItemToHomeList(homeItemList);
                    }
                    loadingDialog.dismiss();
                    Log.e(TAG, "Top movies list is null");
                }
            }
        });
    }

    //Add items to recyclerViewAdapter
    private void addItemToHomeList(List<HomeItem> homeItemList) {
        Log.e(TAG, "Home item list size: " + homeItemList.size());
        movieAdapter.setHomeItemList(homeItemList);
    }

    //Get Trend Item from Trend Result
    private List<ListItem> getTrendMovies(List<TrendResult> trendResultList) {

        List<ListItem> listItemList = new ArrayList<>();
        for (int i = 0; i < trendResultList.size(); i++) {
            int id = trendResultList.get(i).getId();
            String title = trendResultList.get(i).getOriginalTitle();
            String image = trendResultList.get(i).getPosterPath();
            String imageUrl = getResources().getString(R.string.imageUrl_posterpath) + image;

            if (trendResultList.get(i).getMediaType().equals(Constant.MOVIE)) {
                ListItem listItem = new ListItem(id, Constant.MOVIE_TYPE, title, imageUrl);
                listItemList.add(listItem);
            }else {
                ListItem listItem = new ListItem(id, Constant.TV_TYPE, title, imageUrl);
                listItemList.add(listItem);
            }
        }
        return listItemList;
    }

    //Create Loading Dialog
    private AlertDialog createAlertDialgo(Context context){
        View layout = getLayoutInflater().inflate(R.layout.loading_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.TransparentDialog);
        builder.setCancelable(false);
        builder.setView(layout);
        return builder.create();
    }
}