package com.technoidtintin.android.moviesmela;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCredits {

    @SerializedName("cast")
    @Expose
    public List<TvCast> cast = null;
    @SerializedName("id")
    @Expose
    public Integer id;

    public List<TvCast> getCast() {
        return cast;
    }

    public void setCast(List<TvCast> cast) {
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
