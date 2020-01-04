package com.technoidtintin.android.moviesmela.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.HomeItem;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String HOME_TAG = "Home Create";

    private HomeViewModel homeViewModel;
    private RecyclerView parentRecycler;
    private HomeAdapter homeAdapter;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;

    private List<HomeItem> homeItemList = new ArrayList<>();

    private String apiKey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.e(HOME_TAG,HOME_TAG);
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
        getTredingWeek();

        //Initializing SliderView
        sliderView = root.findViewById(R.id.sliderView);
        sliderAdapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(sliderAdapter);
        getTrendingToday();
        sliderView.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setScrollTimeInSec(6);
        return root;
    }

    //Get Trending Today
    private void getTrendingToday(){
        homeViewModel.getTrending(Constant.ALL,Constant.DAY,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {
                if (trending != null){
                    List<TrendResult>trendResultList = trending.getResults();
                    sliderAdapter.setTrendResultList(trendResultList);
                }
            }
        });
    }

    //Get Trending List
    private void getTredingWeek() {
        homeViewModel.getTrending(Constant.ALL,Constant.WEEK,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null) {
                    Log.e(TAG, "Trends are full: " + trending.getTotalResults());
                    List<TrendResult> trendResults = trending.getResults();
                    List<ListItem> listItemList = getTrendItem(trendResults);
                    if (listItemList != null) {
                        Log.e(TAG, "Trends MovieList is full");
                        homeItemList.add(new HomeItem("Trending This Week", listItemList));
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

        homeViewModel.getPopularMovies(apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItemList) {
                if (listItemList != null) {

                    homeItemList.add(new HomeItem("Popular Movies", listItemList));
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
        homeViewModel.getTopRatedMovies(apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null) {
                    Log.e(TAG, "Top movies list is full");
                    homeItemList.add(new HomeItem("Top Rated Movies", listItems));
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
        homeViewModel.getPopularTvShows("popular", apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null) {
                    Log.e(TAG, "Popula Tv Shows list is full");
                    homeItemList.add(new HomeItem("Popular on TV", listItems));
                    if (homeItemList != null) {
                        getTopratedMoveis();
                    }
                }
            }
        });
    }

    //Get Top Rated Tv Shows
    private void getTopRatedTvShows() {
        homeViewModel.getPopularTvShows("top_rated", apiKey).observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (listItems != null) {
                    Log.e(TAG, "Top rated Tv Shows list is full");
                    homeItemList.add(new HomeItem("Top Rated Tv Shows", listItems));
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
    private List<ListItem> getTrendItem(List<TrendResult> trendResultList) {

        List<ListItem> listItemList = new ArrayList<>();
        for (int i = 0; i < trendResultList.size(); i++) {
            int id = trendResultList.get(i).getId();
            String title = trendResultList.get(i).getOriginalTitle();
            String image = trendResultList.get(i).getPosterPath();
            String imageUrl = "http://image.tmdb.org/t/p/w185" + image;
            ListItem listItem = new ListItem(id, title, imageUrl);

            listItemList.add(listItem);
        }
        return listItemList;
    }
}