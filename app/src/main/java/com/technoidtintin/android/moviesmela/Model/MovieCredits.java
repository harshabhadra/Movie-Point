package com.technoidtintin.android.moviesmela.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCredits {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("cast")
    @Expose
    public List<MovieCast> cast = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieCast> getCast() {
        return cast;
    }

    public void setCast(List<MovieCast> cast) {
        this.cast = cast;
    }
}
