package com.technoidtintin.android.moviesmela.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.Repository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private Repository repository;

    public HomeViewModel() {

        repository = Repository.getInstance();
    }

    //Get List of MovieItems
    public LiveData<List<ListItem>>getPopularMovies(String apiKey){

        return repository.getPopularMovies(apiKey);
    }

    //Get List of Top rated movies
    public LiveData<List<ListItem>>getTopRatedMovies(String apiKey){
        return repository.getTopRatedMovies(apiKey);
    }

    //Get list of popular tv shows
    public LiveData<List<ListItem>>getPopularTvShows(String path, String apiKey){
        return repository.getTvShows(path,apiKey);
    }

    public LiveData<Trending>getTrending(String type, String time, String apikey){
        return repository.getTrending(type, time, apikey);
    }
}