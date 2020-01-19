package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Repository;
import com.technoidtintin.android.moviesmela.SeasonDetails;

public class SeasonDetailsViewModel extends ViewModel {

    private Repository repository;

    public SeasonDetailsViewModel() {

        repository = Repository.getInstance();
    }

    //Get Season Details
    public LiveData<SeasonDetails>getSeasonDetails(int tv_id, int seasonNumber , String apiKey){
        return repository.getSeasonDetailsLiveData(tv_id, seasonNumber, apiKey);
    }
}
