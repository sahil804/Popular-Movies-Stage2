package com.example.sahil.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahil.popularmovies.R;
import com.example.sahil.popularmovies.models.Reviews;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private final Context mContext;

    public void setReviewsList(List<Reviews.ReviewItem> reviewsList) {
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }

    private List<Reviews.ReviewItem> reviewsList;

    public ReviewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_review_row, parent, false);
        return new ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapterViewHolder holder, int position) {
        holder.tvAuthor.setText(reviewsList.get(holder.getAdapterPosition()).getAuthor());
        holder.tvContent.setText(reviewsList.get(holder.getAdapterPosition()).getContent());
    }

    @Override
    public int getItemCount() {
        if (null == reviewsList) return 0;
        return reviewsList.size();
    }


    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvContent;

        public ReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}