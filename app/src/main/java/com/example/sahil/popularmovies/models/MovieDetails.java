package com.example.sahil.popularmovies.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("results")
    @Expose
    private List<MovieItem> moviesItemList = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieItem> getMoviesItemList() {
        return moviesItemList;
    }

    public void setMoviesItemList(List<MovieItem> moviesItemList) {
        this.moviesItemList = moviesItemList;
    }


    @Entity(tableName = "moviesDetail")
    public static class MovieItem {

        @Ignore
        @SerializedName("vote_count")
        @Expose
        private int voteCount;

        @PrimaryKey
        @SerializedName("id")
        @Expose
        private int id;

        @Ignore
        @SerializedName("video")
        @Expose
        private boolean video;

        @ColumnInfo(name = "voteAverage")
        @SerializedName("vote_average")
        @Expose
        private double voteAverage;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("popularity")
        @Expose
        private double popularity;

        @ColumnInfo(name = "posterPath")
        @SerializedName("poster_path")
        @Expose
        private String posterPath;

        @Ignore
        @SerializedName("original_language")
        @Expose
        private String originalLanguage;

        @Ignore
        @SerializedName("original_title")
        @Expose
        private String originalTitle;

        @Ignore
        @SerializedName("genre_ids")
        @Expose
        private List<Integer> genreIds = null;

        @SerializedName("backdrop_path")
        @Expose
        private String backdropPath;

        @Ignore
        @SerializedName("adult")
        @Expose
        private boolean adult;

        @SerializedName("overview")
        @Expose
        private String overview;

        @ColumnInfo(name = "releaseDate")
        @SerializedName("release_date")
        @Expose
        private String releaseDate;

        @ColumnInfo(name = "favorite")
        @Expose(serialize = false, deserialize = false)
        private boolean isFavorite;

        public boolean isFavorite() {
            return isFavorite;
        }

        public void setFavorite(boolean favorite) {
            isFavorite = favorite;
        }

        @ColumnInfo(name = "type")
        @Expose(serialize = false, deserialize = false)
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        @Override
        public String toString() {
            return "MovieItem{" +
                    "id=" + id +
                    ", voteAverage=" + voteAverage +
                    ", title='" + title + '\'' +
                    ", popularity=" + popularity +
                    ", originalTitle='" + originalTitle + '\'' +
                    ", overview='" + overview + '\'' +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", isFavorite=" + isFavorite +
                    '}';
        }
    }
}