package com.technoidtintin.android.moviesmela.ui.Movies;

import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Repository;

public class MoviesViewModel extends ViewModel {
    private Repository repository;

    public MoviesViewModel() {

        repository = Repository.getInstance();
    }
//
//    //Get List of MovieItems
//    public LiveData<List<MovieItem>>getPopularMovies(String apiKey){
//
//        return repository.getPopularMovies(apiKey);
//    }

}