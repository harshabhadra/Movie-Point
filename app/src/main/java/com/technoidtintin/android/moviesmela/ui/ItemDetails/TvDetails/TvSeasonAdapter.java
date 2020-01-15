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

    public TvSeasonAdapter(Context context) {
        this.context = context;
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
        Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
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

    class TvSeasonViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public TvSeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.season_image_view);
        }
    }
}
