package com.technoidtintin.android.moviesmela;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SeasonDetailsViewModel extends ViewModel {

    private Repository repository;

    public SeasonDetailsViewModel() {

        repository = Repository.getInstance();
    }

    //Get Season Details
    public LiveData<SeasonDetails>getSeasonDetails(int tv_id, int seasonNumber ,String apiKey){
        return repository.getSeasonDetailsLiveData(tv_id, seasonNumber, apiKey);
    }
}
