package com.technoidtintin.android.moviesmela;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {


    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder{

        private ImageView episodePoster;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);

            episodePoster = itemView.findViewById(R.id.episode_iv);
        }
    }
}
