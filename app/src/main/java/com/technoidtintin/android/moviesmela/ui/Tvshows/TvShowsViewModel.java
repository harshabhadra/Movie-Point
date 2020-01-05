package com.technoidtintin.android.moviesmela.ui.Tvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.Repository;
import com.technoidtintin.android.moviesmela.Model.TvShows;

import java.util.List;

public class TvShowsViewModel extends ViewModel {

    private Repository repository;

    public TvShowsViewModel() {

        repository = Repository.getInstance();
    }

    //Get Trending Tv Shows
    public LiveData<Trending>getTrendingTvShows(String type,String tme, String apiKey){
        return repository.getTrending(type,tme,apiKey);
    }

    //Get List of Tv Shows on Air
    public LiveData<TvShows> getTvShows(String path, String apiKey){
        return repository.getTvShowsOnAir(path,apiKey);
    }
}