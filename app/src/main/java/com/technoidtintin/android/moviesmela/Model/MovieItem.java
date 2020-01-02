package com.technoidtintin.android.moviesmela.Model;

public class MovieItem {

    private int id;
    private String movieTitle;
    private String moviePosterPath;

    public MovieItem(int id, String movieTitle, String moviePosterPath) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }
}
