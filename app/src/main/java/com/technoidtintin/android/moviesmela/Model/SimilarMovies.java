package com.technoidtintin.android.moviesmela.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.technoidtintin.android.moviesmela.Model.SimilarMovieResults;

import java.util.List;

public class SimilarMovies {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<SimilarMovieResults> results = null;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<SimilarMovieResults> getResults() {
        return results;
    }

    public void setResults(List<SimilarMovieResults> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
