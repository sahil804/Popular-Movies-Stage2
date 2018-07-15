package com.example.sahil.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sahil.popularmovies.models.MovieDetails;
import com.example.sahil.popularmovies.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private final Context mContext;
    private MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private List<MovieDetails.MovieItem> movieItems;

    public MovieAdapter(Context mContext, MovieAdapterOnClickHandler movieAdapterOnClickHandler) {
        this.mContext = mContext;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieDetails.MovieItem movieItem);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(com.example.sahil.popularmovies.R.layout.movie_row, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieAdapterViewHolder holder, int position) {
        String url = Constants.BASE_IMAGE_URL + movieItems.get(position).getBackdropPath();
        Picasso.with(mContext).load(url).placeholder(com.example.sahil.popularmovies.R.drawable.ic_launcher_foreground).into(holder.iconMovieView);
    }

    @Override
    public int getItemCount() {
        if (null == movieItems) return 0;
        return movieItems.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView iconMovieView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            this.iconMovieView = itemView.findViewById(com.example.sahil.popularmovies.R.id.movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieAdapterOnClickHandler.onClick(movieItems.get(getAdapterPosition()));
        }
    }

    public void setMovieData(List<MovieDetails.MovieItem> movieData) {
        this.movieItems = movieData;
        notifyDataSetChanged();
    }
}