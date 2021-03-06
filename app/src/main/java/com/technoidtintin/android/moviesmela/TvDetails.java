package com.technoidtintin.android.moviesmela;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.technoidtintin.android.moviesmela.Model.Genre;
import com.technoidtintin.android.moviesmela.Model.LastEpisodeToAir;
import com.technoidtintin.android.moviesmela.Model.NextEpisodeToAir;
import com.technoidtintin.android.moviesmela.Model.Season;

import java.util.List;

@Entity(tableName = "fav_tvshows")
public class TvDetails implements Parcelable {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("episode_run_time")
    @Expose
    @Ignore
    private List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    @Expose
    @Ignore
    private String firstAirDate;
    @SerializedName("genres")
    @Expose
    @Ignore
    private List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    @Ignore
    private String homepage;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private Integer id;
    @SerializedName("in_production")
    @Expose
    @Ignore
    private Boolean inProduction;
    @SerializedName("languages")
    @Expose
    @Ignore
    private List<String> languages = null;
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;
    @SerializedName("last_episode_to_air")
    @Expose
    @Ignore
    private LastEpisodeToAir lastEpisodeToAir;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("next_episode_to_air")
    @Expose
    private NextEpisodeToAir nextEpisodeToAir;
    @SerializedName("number_of_episodes")
    @Expose
    private Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private Integer numberOfSeasons;
    @SerializedName("origin_country")
    @Expose
    @Ignore
    private List<String> originCountry = null;
    @SerializedName("original_language")
    @Expose
    @Ignore
    private String originalLanguage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    @Ignore
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("seasons")
    @Expose
    @ColumnInfo(name = "season_list")
    private List<Season> seasons = null;
    @SerializedName("status")
    @Expose
    @Ignore
    private String status;
    @SerializedName("type")
    @Expose
    @Ignore
    private String type;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    public TvDetails(String backdropPath, @NonNull Integer id, String lastAirDate,
                     String name, NextEpisodeToAir nextEpisodeToAir, Integer numberOfEpisodes,
                     Integer numberOfSeasons, String originalName, String overview, String posterPath,
                     List<Season>seasons, Double voteAverage, Integer voteCount) {
        this.backdropPath = backdropPath;
        this.id = id;
        this.lastAirDate = lastAirDate;
        this.name = name;
        this.nextEpisodeToAir = nextEpisodeToAir;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.originalName = originalName;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasons = seasons;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    protected TvDetails(Parcel in) {
        backdropPath = in.readString();
        firstAirDate = in.readString();
        homepage = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpInProduction = in.readByte();
        inProduction = tmpInProduction == 0 ? null : tmpInProduction == 1;
        languages = in.createStringArrayList();
        lastAirDate = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            numberOfEpisodes = null;
        } else {
            numberOfEpisodes = in.readInt();
        }
        if (in.readByte() == 0) {
            numberOfSeasons = null;
        } else {
            numberOfSeasons = in.readInt();
        }
        originCountry = in.createStringArrayList();
        originalLanguage = in.readString();
        originalName = in.readString();
        overview = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        posterPath = in.readString();
        seasons = in.createTypedArrayList(Season.CREATOR);
        status = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
    }

    public static final Creator<TvDetails> CREATOR = new Creator<TvDetails>() {
        @Override
        public TvDetails createFromParcel(Parcel in) {
            return new TvDetails(in);
        }

        @Override
        public TvDetails[] newArray(int size) {
            return new TvDetails[size];
        }
    };

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getInProduction() {
        return inProduction;
    }

    public void setInProduction(Boolean inProduction) {
        this.inProduction = inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public LastEpisodeToAir getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public void setLastEpisodeToAir(LastEpisodeToAir lastEpisodeToAir) {
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NextEpisodeToAir getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public void setNextEpisodeToAir(NextEpisodeToAir nextEpisodeToAir) {
        this.nextEpisodeToAir = nextEpisodeToAir;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdropPath);
        parcel.writeString(firstAirDate);
        parcel.writeString(homepage);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (inProduction == null ? 0 : inProduction ? 1 : 2));
        parcel.writeStringList(languages);
        parcel.writeString(lastAirDate);
        parcel.writeString(name);
        if (numberOfEpisodes == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfEpisodes);
        }
        if (numberOfSeasons == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numberOfSeasons);
        }
        parcel.writeStringList(originCountry);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalName);
        parcel.writeString(overview);
        if (popularity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(popularity);
        }
        parcel.writeString(posterPath);
        parcel.writeTypedList(seasons);
        parcel.writeString(status);
        parcel.writeString(type);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
        if (voteCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(voteCount);
        }
    }
}
