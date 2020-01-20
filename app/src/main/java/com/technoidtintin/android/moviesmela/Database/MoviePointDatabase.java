package com.technoidtintin.android.moviesmela.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.technoidtintin.android.moviesmela.Dao.FavMovieDao;
import com.technoidtintin.android.moviesmela.Dao.FavTvShowsDao;
import com.technoidtintin.android.moviesmela.DataConverter;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.TvDetails;

@Database(entities = {Movies.class, TvDetails.class}, version = 5, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class MoviePointDatabase extends RoomDatabase {

    private static MoviePointDatabase INSTANCE;

    public abstract FavMovieDao favMovieDao();
    public abstract FavTvShowsDao favTvShowsDao();

    public static MoviePointDatabase getInstance(Context context){

        if (INSTANCE == null){
            synchronized (MoviePointDatabase.class){
                if (INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context,
                            MoviePointDatabase.class, "movie_point_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
