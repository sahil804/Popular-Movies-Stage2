package com.example.sahil.popularmovies.utilities;

import android.content.Context;

import com.example.sahil.popularmovies.models.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();

    private static final String KEY_RESULT = "results";

    private static final String KEY_TITLE = "title";

    private static final String KEY_POSTER_PATH = "poster_path";

    private static final String KEY_BACK_DROP_PATH = "backdrop_path";

    private static final String KEY_OVERVIEW = "overview";

    private static final String KEY_VOTE_AVERAGE = "vote_average";

    private static final String KEY_RELEASE_DATE = "release_date";

    public static List<MovieItem> getMovieItemsFromJson(Context context, String movieJsonStr) throws JSONException {
        List<MovieItem> movieItems = new ArrayList<>();
        JSONObject moviesJson = new JSONObject(movieJsonStr);
        if (!moviesJson.has(KEY_RESULT)) return movieItems;
        JSONArray movieDetailArray = moviesJson.getJSONArray(KEY_RESULT);
        for (int i = 0; i <= movieDetailArray.length() - 1; i++) {
            JSONObject movieDetail = movieDetailArray.getJSONObject(i);
            MovieItem movieItem = new MovieItem();
            movieItem.setMovieTitle(movieDetail.getString(KEY_TITLE));
            movieItem.setThumbNail(movieDetail.getString(KEY_BACK_DROP_PATH));
            movieItem.setPosterPath(movieDetail.getString(KEY_POSTER_PATH));
            movieItem.setPlotSynopsis(movieDetail.getString(KEY_OVERVIEW));
            movieItem.setUserRating(movieDetail.getDouble(KEY_VOTE_AVERAGE));
            movieItem.setReleaseDate(movieDetail.getString(KEY_RELEASE_DATE));
            movieItems.add(movieItem);
        }
        return movieItems;
    }

}
