package com.technoidtintin.android.moviesmela.ui.Tvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Repository;
import com.technoidtintin.android.moviesmela.TvShows;

public class TvShowsViewModel extends ViewModel {

    private Repository repository;

    public TvShowsViewModel() {

        repository = Repository.getInstance();
    }

    //Get List of Tv Shows on Air
    public LiveData<TvShows>getTvShowsOnAir(String path, String apiKey){
        return repository.getTvShowsOnAir(path,apiKey);
    }
}