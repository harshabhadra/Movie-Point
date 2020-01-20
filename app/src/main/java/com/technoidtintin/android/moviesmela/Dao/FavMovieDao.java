package com.technoidtintin.android.moviesmela.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.technoidtintin.android.moviesmela.Movies;

import java.util.List;

@Dao
public interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movies movies);

    @Query("SELECT * from fav_movies ORDER BY id ASC")
    LiveData<List<Movies>>getFavMovieList();

    @Delete
    void deleteFavMovie(Movies movies);
}
