package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class TvSeasonAdapter extends RecyclerView.Adapter<TvSeasonAdapter.TvSeasonViewHolder>{

    private Context context;
    private List<Season>seasonList = new ArrayList<>();
    private OnSeasonItemClickListener seasonItemClickListener;

    public TvSeasonAdapter(Context context, OnSeasonItemClickListener seasonItemClickListener) {
        this.context = context;
        this.seasonItemClickListener = seasonItemClickListener;
    }

    public interface OnSeasonItemClickListener{
        void onSeasonItemClick(int position);
    }
    @NonNull
    @Override
    public TvSeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvSeasonViewHolder(LayoutInflater.from(context).inflate(R.layout.season_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvSeasonViewHolder holder, int position) {

        Season season = seasonList.get(position);
        String imageUrl = "http://image.tmdb.org/t/p/w300" + season.getPosterPath();
        Picasso.get().load(imageUrl).placeholder(R.drawable.tmdb).error(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
        notifyDataSetChanged();
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public Season getSeason(int position){
        return seasonList.get(position);
    }

    class TvSeasonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public TvSeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.season_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            seasonItemClickListener.onSeasonItemClick(position);
        }
    }
}
