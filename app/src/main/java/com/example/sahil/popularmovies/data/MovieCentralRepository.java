package com.example.sahil.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.example.sahil.popularmovies.AppExecutors;
import com.example.sahil.popularmovies.data.database.MovieDetailDao;
import com.example.sahil.popularmovies.data.network.MovieNetworkDataSource;
import com.example.sahil.popularmovies.models.MovieDetails;
import com.example.sahil.popularmovies.models.Reviews;
import com.example.sahil.popularmovies.models.Trailers;
import com.example.sahil.popularmovies.utilities.Constants;

import java.util.List;

import static com.example.sahil.popularmovies.BuildConfig.DEBUG;

public class MovieCentralRepository {

    private static final String TAG = MovieCentralRepository.class.getSimpleName();

    private final AppExecutors mExecutors;
    private final MovieDetailDao mMovieDetailDao;
    private final MovieNetworkDataSource mMovieNetworkDataSource;

    private LiveData<List<MovieDetails.MovieItem>> movieListLiveData;
    private MutableLiveData<MovieDetails.MovieItem> movieItemMutableLiveData = new MutableLiveData<>();

    public LiveData<List<MovieDetails.MovieItem>> getMovieListLiveData(LiveData<String> sortType) {
        LiveData<List<MovieDetails.MovieItem>> mNetworkMovieDetailsLiveData=
                Transformations.switchMap(sortType, sortTypeChange-> mMovieNetworkDataSource.getMovieDetails(sortTypeChange));

        movieListLiveData = Transformations.switchMap(mNetworkMovieDetailsLiveData, movieDetailsLiveDataChange-> {
            Log.d(TAG, "getMovieListLiveData: "+movieDetailsLiveDataChange);
            if(sortType != null && !sortType.getValue().equalsIgnoreCase(Constants.FAVORITE)) {
                mExecutors.diskIO().execute(() -> {
                    mMovieDetailDao.deleteMovieItems(sortType.getValue());
                    mMovieDetailDao.insertAllMovieDetails(movieDetailsLiveDataChange);
                });
            } else {
                Log.d(TAG, "getMovieListLiveData: favorite part");
                return mMovieDetailDao.getFavoriteMovieList();
            }
            return mMovieDetailDao.getMovieList(sortType.getValue());
        });

        return movieListLiveData;
    }

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieCentralRepository sInstance;

    private MovieCentralRepository(MovieDetailDao movieDetailDao, MovieNetworkDataSource movieNetworkDataSource,
                                   AppExecutors executors) {
        mMovieDetailDao = movieDetailDao;
        mMovieNetworkDataSource = movieNetworkDataSource;
        mExecutors = executors;

    }

    public synchronized static MovieCentralRepository getInstance(MovieDetailDao movieDetailDao,
                                                                  MovieNetworkDataSource movieNetworkDataSource, AppExecutors executors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieCentralRepository(movieDetailDao, movieNetworkDataSource,
                        executors);
                if (DEBUG) Log.d(TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<MovieDetails.MovieItem> getMovieDetailsItem(int movieId) {
        mExecutors.diskIO().execute(() -> {
            MovieDetails.MovieItem movieItem = mMovieDetailDao.getMovieItemDetails(movieId);
            if (DEBUG) Log.d(TAG, "getMovieDetailsItem "+movieItem);
            movieItemMutableLiveData.postValue(movieItem);
        });
        return movieItemMutableLiveData;
    }

    public LiveData<List<Trailers.TrailerItem>> getTrailers(int movieId) {
        return mMovieNetworkDataSource.getTrailers(movieId);
    }

    public LiveData<List<Reviews.ReviewItem>> getReviews(int movieId) {
        return mMovieNetworkDataSource.getReviews(movieId);
    }

    public void setAsFavorite(int mMovieItemId, boolean isChecked) {
        mExecutors.diskIO().execute(() -> mMovieDetailDao.updateMovieAsFavorite(mMovieItemId, isChecked));
    }
}