package com.technoidtintin.android.moviesmela.ui.Movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.Repository;

public class MoviesViewModel extends ViewModel {
    private Repository repository;

    public MoviesViewModel() {

        repository = Repository.getInstance();
    }

    public LiveData<Trending> getTrendingMovies(String type, String time, String apikey){
        return repository.getTrending(type, time, apikey);
    }

}