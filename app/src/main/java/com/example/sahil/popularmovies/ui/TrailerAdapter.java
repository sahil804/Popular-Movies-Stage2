package com.example.sahil.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sahil.popularmovies.R;
import com.example.sahil.popularmovies.models.Trailers;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private final Context mContext;
    private final TrailerAdapterOnClickHandler adapterOnClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String key);
    }

    public void setTrailerItemList(List<Trailers.TrailerItem> trailerItemList) {
        this.trailerItemList = trailerItemList;
        notifyDataSetChanged();
    }

    private List<Trailers.TrailerItem> trailerItemList;

    public TrailerAdapter(Context mContext, TrailerAdapterOnClickHandler adapterOnClickHandler) {
        this.mContext = mContext;
        this.adapterOnClickHandler = adapterOnClickHandler;
    }


    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_trailer_row, parent, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        holder.tvTitle.setText(trailerItemList.get(holder.getAdapterPosition()).getName());
    }

    @Override
    public int getItemCount() {
        if (null == trailerItemList) return 0;
        return trailerItemList.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivPlay;
        TextView tvTitle;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ivPlay = itemView.findViewById(R.id.iv_play);
            tvTitle = itemView.findViewById(R.id.tv_trailer_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            adapterOnClickHandler.onClick(trailerItemList.get(getAdapterPosition()).getKey());
        }
    }
}