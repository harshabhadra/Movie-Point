package com.technoidtintin.android.moviesmela;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technoidtintin.android.moviesmela.Model.NextEpisodeToAir;
import com.technoidtintin.android.moviesmela.Model.Season;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public String fromNextEpisodeToString(NextEpisodeToAir nextEpisodeToAir){
        if (nextEpisodeToAir == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NextEpisodeToAir>(){
        }.getType();
        return gson.toJson(nextEpisodeToAir,type);
    }

    @TypeConverter
    public NextEpisodeToAir fromStringToNextEpisode(String nextEpisodeString){
        if (nextEpisodeString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NextEpisodeToAir>(){
        }.getType();
        return gson.fromJson(nextEpisodeString,type);
    }

    @TypeConverter
    public String fromSeasonListToString(List<Season>seasonList){
        if (seasonList == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Season>>(){
        }.getType();

        return gson.toJson(seasonList,type);
    }

    @TypeConverter
    public List<Season> fromStringToListOfSeason(String seasonListString){
        if (seasonListString == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Season>>(){
        }.getType();
        return gson.fromJson(seasonListString,type);
    }
}
