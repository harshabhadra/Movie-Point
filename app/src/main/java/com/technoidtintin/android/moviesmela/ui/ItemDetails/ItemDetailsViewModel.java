package com.technoidtintin.android.moviesmela.ui.ItemDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.Movies;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.Repository;

public class ItemDetailsViewModel extends ViewModel {

    private Repository repository;

    public ItemDetailsViewModel() {

        repository = Repository.getInstance();
    }

    //Get Movies Details
    public LiveData<Movies>getMoviesDetails(String movieId, String apiKey){
        return repository.getMovieDetails(movieId, apiKey);
    }
}
