package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.MovieCredits;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.Repository;

public class MovieDetailsViewModel extends ViewModel {

    private Repository repository;
    public MovieDetailsViewModel() {

        repository = Repository.getInstance();
    }
    //Get Movies Details
    public LiveData<Movies> getMoviesDetails(int movieId, String apiKey){
        return repository.getMovieDetails(movieId, apiKey);
    }

    //Get Movie Credits
    public LiveData<MovieCredits>getMovieCredits(int movieId, String apiKey){
        return repository.getMovieCredits(movieId, apiKey);
    }
}
