package com.technoidtintin.android.moviesmela.ui.Favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.technoidtintin.android.moviesmela.Repository;
import com.technoidtintin.android.moviesmela.TvDetails;

import java.util.List;

public class TvFavViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<TvDetails>>favTvShowList;

    public TvFavViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        favTvShowList = repository.getFavTvShowsList();
    }

    //Get Fav Tv shows list
    public LiveData<List<TvDetails>>getFavTvShowList(){
        return favTvShowList;
    }

    //Insert Tv shows to fav list
    public void insertTvShowsToFav(TvDetails tvDetails){
        repository.insertTvShowsToFav(tvDetails);
    }

    //Delete Tv shows from fav list
    public void deleteTvShowsFromFav(TvDetails tvDetails){
        repository.deleteTvShowFromFav(tvDetails);
    }
}
