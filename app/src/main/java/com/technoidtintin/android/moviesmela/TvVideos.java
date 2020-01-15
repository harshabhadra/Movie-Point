package com.technoidtintin.android.moviesmela;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvVideos {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TvVideoResult> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TvVideoResult> getResults() {
        return results;
    }

    public void setResults(List<TvVideoResult> results) {
        this.results = results;
    }
}
