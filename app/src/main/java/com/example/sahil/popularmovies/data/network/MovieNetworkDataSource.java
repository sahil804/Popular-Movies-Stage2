package com.example.sahil.popularmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.sahil.popularmovies.BuildConfig;
import com.example.sahil.popularmovies.models.MovieDetails;
import com.example.sahil.popularmovies.models.Reviews;
import com.example.sahil.popularmovies.models.Trailers;
import com.example.sahil.popularmovies.utilities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieNetworkDataSource {

    private static final String TAG = MovieNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieNetworkDataSource sInstance;
    private MutableLiveData<List<MovieDetails.MovieItem>> movieListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trailers.TrailerItem>> listTrailerMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<Reviews.ReviewItem>> listReviewMutableLiveData = new MutableLiveData<>();

    /**
     * Get the singleton for this class
     */
    public static MovieNetworkDataSource getInstance() {
        Log.d(TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkDataSource();
                Log.d(TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<MovieDetails.MovieItem>> getMovieDetails(String sortTypeChange) {
        Log.d(TAG, "getMovieDetails: "+sortTypeChange);
        if(!sortTypeChange.equalsIgnoreCase(Constants.FAVORITE)) {
            ApiInterface apiInterface = ServiceBuilder.buildService(ApiInterface.class);
            Call<MovieDetails> movieDetailsCall;
            movieDetailsCall = apiInterface.getMovieDetails(sortTypeChange, BuildConfig.MY_MOVIE_DB_API_KEY);
            movieDetailsCall.enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                    MovieDetails movieDetails = response.body();
                    Log.d(TAG, "onResponse" + response.body());
                    if (movieDetails != null && movieDetails.getMoviesItemList().size() > 0) {
                        Log.d(TAG, "onResponse size:" + movieDetails.getMoviesItemList().size());
                        List<MovieDetails.MovieItem> movieItems = movieDetails.getMoviesItemList();
                        for (MovieDetails.MovieItem movieItem : movieItems) {
                            movieItem.setType(sortTypeChange);
                        }
                        movieListLiveData.postValue(movieDetails.getMoviesItemList());
                    }
                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {

                }
            });
        } else {
            Log.d(TAG, "getMovieDetails null: "+sortTypeChange);
            movieListLiveData.setValue(new MovieDetails().getMoviesItemList());
        }
        return movieListLiveData;
    }

    public LiveData<List<Trailers.TrailerItem>> getTrailers(int movieId) {
        ApiInterface apiInterface = ServiceBuilder.buildService(ApiInterface.class);
        Call<Trailers> trailersCall;
        trailersCall = apiInterface.getTrailers(movieId, BuildConfig.MY_MOVIE_DB_API_KEY);
        trailersCall.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                if(response.body() != null) {
                    Log.d(TAG, "onResponse trailer " + response.body().getResults().size());
                    listTrailerMutableLiveData.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {

            }
        });

        return listTrailerMutableLiveData;
    }

    public LiveData<List<Reviews.ReviewItem>> getReviews(int movieId) {
        ApiInterface apiInterface = ServiceBuilder.buildService(ApiInterface.class);
        Call<Reviews> reviewsCall;
        reviewsCall = apiInterface.getReviews(movieId, BuildConfig.MY_MOVIE_DB_API_KEY);
        reviewsCall.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if(response.body() != null) {
                    Log.d(TAG, "onResponse reviews " + response.body().getResults().size());
                    listReviewMutableLiveData.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        });
        return listReviewMutableLiveData;
    }
}