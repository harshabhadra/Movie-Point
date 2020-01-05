package com.technoidtintin.android.moviesmela.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {

    private int id;
    private String type;
    private String movieTitle;
    private String moviePosterPath;

    public ListItem(int id,String type, String movieTitle, String moviePosterPath) {
        this.id = id;
        this.type = type;
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected ListItem(Parcel in) {
        id = in.readInt();
        type = in.readString();
        movieTitle = in.readString();
        moviePosterPath = in.readString();
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePosterPath);
    }
}
