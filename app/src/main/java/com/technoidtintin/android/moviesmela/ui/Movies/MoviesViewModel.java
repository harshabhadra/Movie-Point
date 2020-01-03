package com.technoidtintin.android.moviesmela.ui.Movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.Repository;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private Repository repository;

    public MoviesViewModel() {

        repository = Repository.getInstance();
    }

    //Get Trending Movies
    public LiveData<Trending> getTrendingMovies(String type, String time, String apikey){
        return repository.getTrending(type, time, apikey);
    }

    //Get List of MovieItems
    public LiveData<List<ListItem>>getPopularMovies(String apiKey){

        return repository.getPopularMovies(apiKey);
    }

    //Get List of Top rated movies
    public LiveData<List<ListItem>>getTopRatedMovies(String apiKey){
        return repository.getTopRatedMovies(apiKey);
    }

    //Get List of Now Playing Movies
    public LiveData<List<ListItem>>getNowPlayingMovies(String apiKey){
        return repository.getNowPlayingMovies(apiKey);
    }
}