package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.TvCast;

import java.util.ArrayList;
import java.util.List;

public class TvCastAdapters extends RecyclerView.Adapter<TvCastAdapters.TvCastViewHolder> {

    private Context context;
    private List<TvCast>tvCastList = new ArrayList<>();

    public TvCastAdapters(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TvCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvCastViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvCastViewHolder holder, int position) {

        TvCast tvCast = tvCastList.get(position);
        String posterPath = context.getResources().getString(R.string.imageUrl_posterpath) + tvCast.getProfilePath();
        Picasso.get().load(posterPath)
                .placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .into(holder.castImageView);
    }

    @Override
    public int getItemCount() {
        return tvCastList.size();
    }

    public void setTvCastList(List<TvCast> tvCastList) {
        this.tvCastList = tvCastList;
        notifyDataSetChanged();
    }

    class TvCastViewHolder extends RecyclerView.ViewHolder{

        private ImageView castImageView;

        public TvCastViewHolder(@NonNull View itemView) {
            super(itemView);

            castImageView = itemView.findViewById(R.id.cast_image_view);
        }
    }
}
