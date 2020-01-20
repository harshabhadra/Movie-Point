package com.technoidtintin.android.moviesmela.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.technoidtintin.android.moviesmela.TvDetails;

import java.util.List;

@Dao
public interface FavTvShowsDao {

    @Insert
    void insertFavTvShow(TvDetails tvDetails);

    @Query("SELECT * from fav_tvshows ORDER BY id ASC")
    LiveData<List<TvDetails>>getFavTvShows();

    @Delete
    void delteFavTvShow(TvDetails tvDetails);
}
