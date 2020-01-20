package com.technoidtintin.android.moviesmela.ui.home;

import android.app.AlertDialog;
import android.content.Context;
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
import com.technoidtintin.android.moviesmela.Model.TvShows;
import com.technoidtintin.android.moviesmela.Model.TvShowsList;
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

    private String trendMediaType;

    private List<HomeItem> homeItemList = new ArrayList<>();

    private String apiKey;

    private AlertDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.e(HOME_TAG,HOME_TAG);
        //Initializing api key
        apiKey = getResources().getString(R.string.api_key);

        //Initializing HomeViewModel class
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Initializing loading Dialog
        loadingDialog = createAlertDialgo(getContext());
        loadingDialog.show();

        //Initializing parentRecyclerView
        parentRecycler = root.findViewById(R.id.parent_recycler);
        parentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter(getContext());
        parentRecycler.setAdapter(homeAdapter);
        getTredingWeek();

        //Initializing SliderView
        sliderView = root.findViewById(R.id.sliderView);
        sliderAdapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(sliderAdapter);
        getTrendingToday();
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
                    getPopularTvShows();
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
                    getTopRatedTvShows();
                    Log.e(TAG, "Top movies list is null");
                }
            }
        });
    }

    //Get Popular Tv Shows
    private void getPopularTvShows() {
        homeViewModel.getTvShows(Constant.POPULAR,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {

                if (tvShows !=null){
                    List<TvShowsList>tvShowsLists = tvShows.getResults();
                    List<ListItem>listItemList = getTvShowsList(tvShowsLists);
                    if (listItemList != null){
                        homeItemList.add(new HomeItem("Popular Tv Shows", listItemList));

                        if (homeItemList != null) {
                            getTopratedMoveis();
                        }
                    }
                }else {
                    getTopratedMoveis();
                }
            }
        });
    }

    //Get Top Rated Tv Shows
    private void getTopRatedTvShows() {

        homeViewModel.getTvShows(Constant.TOP_RATED,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {


                if (tvShows !=null){
                    loadingDialog.dismiss();
                    List<TvShowsList>tvShowsLists = tvShows.getResults();
                    List<ListItem>listItemList = getTvShowsList(tvShowsLists);
                    if (listItemList != null){
                        homeItemList.add(new HomeItem("Top Rated Tv Shows", listItemList));
                        addItemToHomeList(homeItemList);
                    }
                }else {
                    loadingDialog.dismiss();
                    if (homeItemList != null){
                        addItemToHomeList(homeItemList);

                    }
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

    //Get Tv shows List
    private List<ListItem>getTvShowsList(List<TvShowsList>showsLists){
        List<ListItem>listItemList = new ArrayList<>();
        for (int i=0; i<showsLists.size();i++){
            int id = showsLists.get(i).getId();
            String title = showsLists.get(i).getOriginalName();
            String imagePath = showsLists.get(i).getPosterPath();
            String image = getResources().getString(R.string.imageUrl_posterpath) + imagePath;
            ListItem listItem = new ListItem(id,Constant.TV_TYPE,title,image);

            listItemList.add(listItem);
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