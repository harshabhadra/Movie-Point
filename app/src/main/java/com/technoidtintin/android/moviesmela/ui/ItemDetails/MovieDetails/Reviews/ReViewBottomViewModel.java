package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.Reviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.ReViewsList;
import com.technoidtintin.android.moviesmela.Repository;

public class ReViewBottomViewModel extends ViewModel {

    private Repository repository;

    public ReViewBottomViewModel() {

        repository = Repository.getInstance();
    }

    //Get Movie Review List
    public LiveData<ReViewsList> getMovieReviews(int movie_id, String apiKey){
        return repository.getMovieReviewList(movie_id, apiKey);
    }
}
