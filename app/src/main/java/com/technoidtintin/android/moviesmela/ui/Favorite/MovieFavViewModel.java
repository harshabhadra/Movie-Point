package com.technoidtintin.android.moviesmela.ui.Favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.Repository;

import java.util.List;

public class MovieFavViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Movies>>favMovieList;

    public MovieFavViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        favMovieList = repository.getFavMoives();
    }

    //Get Favorite Movies
    public LiveData<List<Movies>>getFavMovieList(){
        return favMovieList;
    }

    //Insert Movie into fav list
    public void insertMovieToFav(Movies movies){
        repository.insertMoviesToFav(movies);
    }

    //Delete Movie from Fav
    public void deleteMovieFromFav(Movies movies){
        repository.deleteMovieFromFav(movies);
    }
}
