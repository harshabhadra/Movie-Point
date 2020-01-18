package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Episode;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private Context context;
    private List<Episode>episodeList = new ArrayList<>();

    public EpisodeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(context).inflate(R.layout.episode_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {

        Episode episode = episodeList.get(position);
        String posterImage = context.getResources().getString(R.string.imageUrl_posterpath_small) + episode.getStillPath();
        Log.e("EpisodeAdapter", " Image url : " + posterImage);
        Picasso.get().load(posterImage).error(R.drawable.tmdb).into(holder.episodePoster);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
        notifyDataSetChanged();
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder{

        private ImageView episodePoster;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);

            episodePoster = itemView.findViewById(R.id.episode_iv);
        }
    }
}
