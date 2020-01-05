package com.technoidtintin.android.moviesmela.ui.Tvshows;

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

import com.technoidtintin.android.moviesmela.Model.HomeItem;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.TvShows;
import com.technoidtintin.android.moviesmela.Model.TvShowsList;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentTvshowsBinding;

import java.util.ArrayList;
import java.util.List;

public class TvShowsFragment extends Fragment {

    private static final String TAG = TvShowsFragment.class.getSimpleName();
    private TvShowsViewModel tvShowsViewModel;
    private FragmentTvshowsBinding tvshowsBinding;
    private TvSliderAdapter sliderAdapter;
    private TvAdapter tvAdapter;
    private String apiKey;
    private List<HomeItem>homeItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Initializing ViewModel Class
        tvShowsViewModel =
                ViewModelProviders.of(this).get(TvShowsViewModel.class);

        //Initializing DataBinding
        tvshowsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_tvshows,container,false);

        //Initializing Api key
        apiKey = getResources().getString(R.string.api_key);

        //Initializing SliderView
        sliderAdapter = new TvSliderAdapter(getContext());
        tvshowsBinding.tvSliderView.setSliderAdapter(sliderAdapter);
        tvshowsBinding.tvSliderView.setScrollTimeInSec(6);
        getTvShowsTrendingToday();

        //Setting up Tv recyclerView
        tvshowsBinding.tvRecycler.setHasFixedSize(true);
        tvshowsBinding.tvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        tvAdapter = new TvAdapter(getContext());
        tvshowsBinding.tvRecycler.setAdapter(tvAdapter);
        getTrendingTvThisWeek();

        return tvshowsBinding.getRoot();
    }

    //Get Tv shows Trending Today
    private void getTvShowsTrendingToday(){
        tvShowsViewModel.getTrendingTvShows(Constant.TV,Constant.DAY,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null){
                    List<TrendResult>trendResultList = trending.getResults();
                    sliderAdapter.setTrendResultList(trendResultList);
                }
            }
        });
    }

    //Getting List of Trending Tv shows This week
    private void getTrendingTvThisWeek(){
        tvShowsViewModel.getTrendingTvShows(Constant.TV,Constant.WEEK,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null){
                    List<TrendResult>trendResultList = trending.getResults();
                    List<ListItem>listItemList = getTrendTVShows(trendResultList);
                    if (listItemList != null){
                        homeItemList.add(new HomeItem("Trending This Week",listItemList));
                        getTvShowsOnAir();
                    }
                }
            }
        });
    }

    //Get list of Tv Shows on Air This week
    private void getTvShowsOnAir(){
        tvShowsViewModel.getTvShows(Constant.ON_THE_AIR,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {

                if (tvShows != null){
                    Log.e(TAG,"Tv Shows on air list is full");
                    List<TvShowsList>tvShowsListList = tvShows.getResults();
                    List<ListItem>showsList = getTvShowsList(tvShowsListList);
                    if (showsList != null){
                        Log.e(TAG,"Tv Shows list is full");
                        homeItemList.add(new HomeItem("Shows On Air This Week",showsList));
                        if (homeItemList != null){
                            getShowsOnAirToday();
                        }
                    }
                }
            }
        });
    }

    //Get Shows on Air Today
    private void getShowsOnAirToday(){
        tvShowsViewModel.getTvShows(Constant.AIR_TODYA,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {

                if (tvShows != null){
                    Log.e(TAG,"Tv Shows on air list is full");
                    List<TvShowsList>tvShowsListList = tvShows.getResults();
                    List<ListItem>showsList = getTvShowsList(tvShowsListList);
                    if (showsList != null){
                        Log.e(TAG,"Tv Shows list is full");
                        homeItemList.add(new HomeItem("Shows On Air Today",showsList));
                        if (homeItemList != null){
                            getPopularTvShows();
                        }
                    }
                }
            }
        });
    }

    //Get Popular TvShows List
    private void getPopularTvShows(){
        tvShowsViewModel.getTvShows(Constant.POPULAR,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {
                if (tvShows != null){
                    List<TvShowsList>tvShowsList = tvShows.getResults();
                    List<ListItem>listItemList = getTvShowsList(tvShowsList);
                    if (listItemList != null){
                        homeItemList.add(new HomeItem("Popular Tv Shows",listItemList));
                        if (homeItemList != null){
                            getTopRatedTVShows();
                        }
                    }
                }
            }
        });
    }

    //Get Top Rated Tv Shows
    private void getTopRatedTVShows(){
        tvShowsViewModel.getTvShows(Constant.TOP_RATED,apiKey).observe(this, new Observer<TvShows>() {
            @Override
            public void onChanged(TvShows tvShows) {
                if (tvShows != null){
                    List<TvShowsList>tvShowsList = tvShows.getResults();
                    List<ListItem>listItemList = getTvShowsList(tvShowsList);
                    if (listItemList != null){
                        homeItemList.add(new HomeItem("Top Rated Tv Shows",listItemList));
                        if (homeItemList != null){
                            addItemsToAdapter(homeItemList);
                        }
                    }
                }
            }
        });
    }

    //Set all list items to the adapter
    private void addItemsToAdapter(List<HomeItem>homeItems){
        tvAdapter.setHomeItemList(homeItems);
    }

    //Get Tv shows List
    private List<ListItem>getTvShowsList(List<TvShowsList>showsLists){
        List<ListItem>listItemList = new ArrayList<>();
        for (int i=0; i<showsLists.size();i++){
            int id = showsLists.get(i).getId();
            String title = showsLists.get(i).getOriginalName();
            String imagePath = showsLists.get(i).getPosterPath();
            String image = "http://image.tmdb.org/t/p/w185" + imagePath;
            ListItem listItem = new ListItem(id,Constant.TV_TYPE,title,image);

            listItemList.add(listItem);
        }
        return listItemList;
    }

    //Get Trend Item from Trend Result
    private List<ListItem> getTrendTVShows(List<TrendResult> trendResultList) {

        List<ListItem> listItemList = new ArrayList<>();
        for (int i = 0; i < trendResultList.size(); i++) {
            int id = trendResultList.get(i).getId();
            String title = trendResultList.get(i).getOriginalTitle();
            String image = trendResultList.get(i).getPosterPath();
            String imageUrl = "http://image.tmdb.org/t/p/w185" + image;
            ListItem listItem = new ListItem(id,Constant.TV_TYPE, title, imageUrl);

            listItemList.add(listItem);
        }
        return listItemList;
    }
}