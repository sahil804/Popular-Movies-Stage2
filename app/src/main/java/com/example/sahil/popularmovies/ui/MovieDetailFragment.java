package com.example.sahil.popularmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahil.popularmovies.R;
import com.example.sahil.popularmovies.utilities.Constants;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private static final String MOVIE_ITEM_ID = "movie_item_id";

    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;

    private TextView tvTrailer, tvReview;
    private CheckBox cbFavorite;

    private TrailerAdapter mTrailerAdapter;
    private ReviewsAdapter mReviewsAdaper;

    private MovieDetailViewModel mViewModel;
    private int mMovieItemId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    public static MovieDetailFragment newInstance(int movieItemId) {

        Bundle args = new Bundle();
        args.putInt(MOVIE_ITEM_ID, movieItemId);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(MOVIE_ITEM_ID)) {
            mMovieItemId = getArguments().getInt(MOVIE_ITEM_ID);
        }

        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        //mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail_layout, container, false);
        mTrailerRecyclerView = rootView.findViewById(R.id.rv_trailer);
        mReviewRecyclerView = rootView.findViewById(R.id.rv_review);

        setupTrailerRecyclerView();
        setupReviewsRecyclerView();
        handleBaseView(rootView);
        updateFavorite();
        return rootView;
    }

    private void updateFavorite() {
        cbFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Log.d(TAG, "onCheckedChanged: "+isChecked);
            mViewModel.setAsFavorite(mMovieItemId, isChecked);
        });
    }

    private void handleBaseView(View rootView) {
        tvTrailer = rootView.findViewById(R.id.tv_trailer);
        tvTrailer.setText(R.string.trailers);
        tvReview = rootView.findViewById(R.id.tv_review);
        tvReview.setText(R.string.reviews);
        cbFavorite = rootView.findViewById(R.id.cb_favorite);
        if (mMovieItemId != -1) {
            mViewModel.getMovieItem(mMovieItemId).observe(this,movieItem -> {
                if(movieItem != null) {
                    String url = Constants.BASE_IMAGE_HD_URL + movieItem.getPosterPath();
                    Picasso.with(getActivity()).load(url).placeholder(R.drawable.ic_launcher_foreground)
                            .into((ImageView) rootView.findViewById(R.id.iv_poster));

                    url = Constants.BASE_IMAGE_URL + movieItem.getBackdropPath();
                    Picasso.with(getActivity()).load(url).placeholder(R.drawable.ic_launcher_foreground)
                            .into((ImageView) rootView.findViewById(R.id.iv_backdropPath));
                    ((TextView) rootView.findViewById(R.id.tv_releasedate)).setText(movieItem.getReleaseDate());
                    ((TextView) rootView.findViewById(R.id.tv_rating)).setText(String.valueOf(movieItem.getVoteAverage()) + "/" + Constants.DEFAULT_RATING);
                    ((TextView) rootView.findViewById(R.id.tv_overview)).setText(movieItem.getOverview());
                    cbFavorite.setChecked(movieItem.isFavorite());
                }
            });
        }
    }

    private void setupTrailerRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTrailerRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerRecyclerView.addItemDecoration(new DividerItemDecoration(mTrailerRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mTrailerAdapter = new TrailerAdapter(getActivity(), this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mViewModel.getTrailers(mMovieItemId).observe(this, trailerItems -> {
            Log.d(TAG, "setupTrailerRecyclerView: "+trailerItems);
            mTrailerAdapter.setTrailerItemList(trailerItems);
            if(trailerItems != null && trailerItems.size() > 0) {
                tvTrailer.setVisibility(View.VISIBLE);
                mTrailerRecyclerView.setVisibility(View.VISIBLE);
            } else {
                tvTrailer.setVisibility(View.GONE);
                mTrailerRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void setupReviewsRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.addItemDecoration(new DividerItemDecoration(mReviewRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mReviewsAdaper = new ReviewsAdapter(getActivity());
        mReviewRecyclerView.setAdapter(mReviewsAdaper);
        mViewModel.getReviews(mMovieItemId).observe(this, reviewItems -> {
            Log.d(TAG, "setupReviewsRecyclerView: "+reviewItems);
            mReviewsAdaper.setReviewsList(reviewItems);
            if(reviewItems != null && reviewItems.size() > 0) {
                tvReview.setVisibility(View.VISIBLE);
                mReviewRecyclerView.setVisibility(View.VISIBLE);
            } else {
                tvReview.setVisibility(View.GONE);
                mReviewRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(String key) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Constants.YOUTUBE_URL + key));
        startActivity(intent);
    }
}