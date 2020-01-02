package com.technoidtintin.android.moviesmela.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;
import com.technoidtintin.android.moviesmela.Adapters.HomeAdapter;
import com.technoidtintin.android.moviesmela.Model.HomeItem;
import com.technoidtintin.android.moviesmela.Model.MovieItem;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private RecyclerView parentRecycler;
    private HomeAdapter homeAdapter;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;

    private List<HomeItem> homeItemList = new ArrayList<>();

    private String apiKey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Initializing api key
        apiKey = getResources().getString(R.string.api_key);

        //Initializing HomeViewModel class
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Initializing parentRecyclerView
        parentRecycler = root.findViewById(R.id.parent_recycler);
        parentRecycler.setHasFixedSize(true);
        parentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter(getContext());
        parentRecycler.setAdapter(homeAdapter);
        getTredingToday();

        //Initializing SliderView
        sliderView = root.findViewById(R.id.sliderView);
        sliderAdapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setScrollTimeInSec(4);
        return root;
    }

    //Get Trending List
    private void getTredingToday() {
        homeViewModel.getTrending(apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null) {
                    Log.e(TAG, "Trends are full: " + trending.getTotalResults());
                    List<TrendResult> trendResults = trending.getResults();
                    sliderAdapter.setTrendResultList(trendResults);
                    List<MovieItem> movieItemList = getTrendItem(trendResults);
                    if (movieItemList != null) {
                        Log.e(TAG, "Trends MovieList is full");
                        homeItemList.add(new HomeItem("Trending Today", movieItemList));
                        if (homeItemList != null) {
                            getPopularMovieList();
                        }
                    } else {
                        Log.e(TAG, "Trends movie list is empty");
                        getPopularMovieList();
                    }
                } else {
                    Log.e(TAG, "Trends are empty");
                    getPopularMovieList();
                }
            }
        });
    }

    //Get Popular Movies
    private void getPopularMovieList() {

        homeViewModel.getPopularMovies(apiKey).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItemList) {
                if (movieItemList != null) {

                    homeItemList.add(new HomeItem("Popular Movies", movieItemList));
                    if (homeItemList != null) {
                        getPopularTvShows();
                    }
                } else {
                    Log.e(TAG, "Movie list is null");
                }
            }
        });
    }

    //Get top rated movies
    private void getTopratedMoveis() {
        homeViewModel.getTopRatedMovies(apiKey).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {
                if (movieItems != null) {
                    Log.e(TAG, "Top movies list is full");
                    homeItemList.add(new HomeItem("Top Rated Movies", movieItems));
                    if (homeItemList != null) {
                        getTopRatedTvShows();
                    }
                } else {
                    Log.e(TAG, "Top movies list is null");
                }
            }
        });
    }

    //Get Popular Tv Shows
    private void getPopularTvShows() {
        homeViewModel.getPopularTvShows("popular", apiKey).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {
                if (movieItems != null) {
                    Log.e(TAG, "Popula Tv Shows list is full");
                    homeItemList.add(new HomeItem("Popular on TV", movieItems));
                    if (homeItemList != null) {
                        getTopratedMoveis();
                    }
                }
            }
        });
    }

    //Get Top Rated Tv Shows
    private void getTopRatedTvShows() {
        homeViewModel.getPopularTvShows("top_rated", apiKey).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {
                if (movieItems != null) {
                    Log.e(TAG, "Top rated Tv Shows list is full");
                    homeItemList.add(new HomeItem("Top Rated Tv Shows", movieItems));
                    addItemToHomeList(homeItemList);
                }
            }
        });
    }

    //Add items to recyclerViewAdapter
    private void addItemToHomeList(List<HomeItem> homeItemList) {
        Log.e(TAG, "Home item list size: " + homeItemList.size());
        homeAdapter.setHomeItemList(homeItemList);
    }

    //Get Trend Item from Trend Result
    private List<MovieItem> getTrendItem(List<TrendResult> trendResultList) {

        List<MovieItem> movieItemList = new ArrayList<>();
        for (int i = 0; i < trendResultList.size(); i++) {
            int id = trendResultList.get(i).getId();
            String title = trendResultList.get(i).getOriginalTitle();
            String image = trendResultList.get(i).getPosterPath();
            String imageUrl = "http://image.tmdb.org/t/p/w185" + image;
            MovieItem movieItem = new MovieItem(id, title, imageUrl);

            movieItemList.add(movieItem);
        }
        return movieItemList;
    }
}