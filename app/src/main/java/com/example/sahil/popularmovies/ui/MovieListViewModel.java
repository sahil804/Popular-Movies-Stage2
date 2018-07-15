package com.example.sahil.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.sahil.popularmovies.AppExecutors;
import com.example.sahil.popularmovies.data.MovieCentralRepository;
import com.example.sahil.popularmovies.data.database.MovieDatabase;
import com.example.sahil.popularmovies.data.network.MovieNetworkDataSource;
import com.example.sahil.popularmovies.models.MovieDetails;

import java.util.List;

public class MovieListViewModel extends AndroidViewModel {

    private MovieCentralRepository mMovieCentralRepository;

    public void setOrder(String orderChange) {
        order.setValue(orderChange);
    }

    private MutableLiveData<String> order = new MutableLiveData<>();

    public LiveData<List<MovieDetails.MovieItem>> getMovieListLiveData() {
        mMovieCentralRepository = MovieCentralRepository.getInstance(MovieDatabase.getInstance(getApplication()).movieDetailDao(),
                MovieNetworkDataSource.getInstance(), AppExecutors.getInstance());
        return mMovieCentralRepository.getMovieListLiveData(order);
    }

    private final MutableLiveData<List<MovieDetails.MovieItem>> mMovieList = new MutableLiveData();

    public MovieListViewModel(@NonNull Application application) {
        super(application);
    }
}