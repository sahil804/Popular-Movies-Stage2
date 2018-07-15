package com.example.sahil.popularmovies.data.network;

import com.example.sahil.popularmovies.models.MovieDetails;
import com.example.sahil.popularmovies.models.Reviews;
import com.example.sahil.popularmovies.models.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/{sortBy}")
    Call<MovieDetails> getMovieDetails(@Path("sortBy") String sortBy, @Query("api_key") String apikey);

    @GET("movie/{id}/videos")
    Call<Trailers> getTrailers(@Path("id") int movieId, @Query("api_key") String apikey);

    @GET("movie/{id}/reviews")
    Call<Reviews> getReviews(@Path("id") int movieId, @Query("api_key") String apikey);
}