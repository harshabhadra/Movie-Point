package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.SimilarTv;
import com.technoidtintin.android.moviesmela.Repository;
import com.technoidtintin.android.moviesmela.TvCredits;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.TvVideos;

public class TvDetailsViewModel extends ViewModel {

    private Repository repository;
    public TvDetailsViewModel() {

        repository = Repository.getInstance();
    }

    //Get TvShows Details
    public LiveData<TvDetails>getTvShowsDetails(int id, String apiKey){
        return repository.getTvDetails(id, apiKey);
    }

    //Get Tv Videos list
    public LiveData<TvVideos>getTvVideos(String tv_id, String apiKey){
        return repository.getTvVideosList(tv_id, apiKey);
    }

    //Get Similar Tv Shows
    public LiveData<SimilarTv>getSimilarTVShows(int id,String apiKey){
        return repository.getSimilarTvShows(id, apiKey);
    }

    //Get Tv Credit
    public LiveData<TvCredits>getTvCredits(int tv_id, String apiKey){
        return repository.getTvCredits(tv_id, apiKey);
    }
}
