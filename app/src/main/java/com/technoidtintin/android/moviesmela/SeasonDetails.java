package com.technoidtintin.android.moviesmela;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonDetails {
    @SerializedName("episodes")
    @Expose
    public List<Episode> episodes = null;

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
