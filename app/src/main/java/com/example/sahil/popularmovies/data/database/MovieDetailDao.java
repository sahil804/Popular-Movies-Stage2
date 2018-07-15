package com.example.sahil.popularmovies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.sahil.popularmovies.models.MovieDetails;

import java.util.List;

@Dao
public interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllMovieDetails(List<MovieDetails.MovieItem> movieItemList);

    @Query("SELECT * FROM moviesDetail WHERE id = :movieId")
    MovieDetails.MovieItem getMovieItemDetails(int movieId);

    @Query("DELETE FROM moviesDetail WHERE type =:sortType AND NOT favorite")
    int deleteMovieItems(String sortType);

    @Query("SELECT * FROM moviesDetail WHERE type = :sortType")
    LiveData<List<MovieDetails.MovieItem>> getMovieList(String sortType);

    @Query("SELECT * FROM moviesDetail WHERE favorite")
    LiveData<List<MovieDetails.MovieItem>> getFavoriteMovieList();

    @Query("UPDATE moviesDetail SET favorite = :favorite  WHERE id = :movieId")
    int updateMovieAsFavorite(int movieId, boolean favorite);
    
}