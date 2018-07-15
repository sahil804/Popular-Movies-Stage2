package com.example.sahil.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sahil.popularmovies.AppExecutors;
import com.example.sahil.popularmovies.data.MovieCentralRepository;
import com.example.sahil.popularmovies.data.database.MovieDatabase;
import com.example.sahil.popularmovies.data.network.MovieNetworkDataSource;
import com.example.sahil.popularmovies.models.MovieDetails;
import com.example.sahil.popularmovies.models.Reviews;
import com.example.sahil.popularmovies.models.Trailers;

import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {

    private MovieCentralRepository mMovieCentralRepository;

    public LiveData<MovieDetails.MovieItem> getMovieItem(int movieId) {
        return mMovieCentralRepository.getMovieDetailsItem(movieId);
    }

    public LiveData<List<Trailers.TrailerItem>> getTrailers(int movieId) {
        return mMovieCentralRepository.getTrailers(movieId);
    }

    public LiveData<List<Reviews.ReviewItem>> getReviews(int movieId) {
        return mMovieCentralRepository.getReviews(movieId);
    }

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        mMovieCentralRepository = MovieCentralRepository.getInstance(MovieDatabase.getInstance(getApplication()).movieDetailDao(),
                MovieNetworkDataSource.getInstance(), AppExecutors.getInstance());
    }

    public void setAsFavorite(int mMovieItemId, boolean isChecked) {
        mMovieCentralRepository.setAsFavorite(mMovieItemId, isChecked);
    }
}