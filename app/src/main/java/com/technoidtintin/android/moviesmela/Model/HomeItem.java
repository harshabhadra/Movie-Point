package com.technoidtintin.android.moviesmela.Model;

import java.util.List;

public class HomeItem {

    private String typeTitle;
    private List<MovieItem>movieItemList;

    public HomeItem(String typeTitle, List<MovieItem> movieItemList) {
        this.typeTitle = typeTitle;
        this.movieItemList = movieItemList;
    }

    public HomeItem() {
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public List<MovieItem> getMovieItemList() {
        return movieItemList;
    }

    public void setMovieItemList(List<MovieItem> movieItemList) {
        this.movieItemList = movieItemList;
    }
}
