package com.technoidtintin.android.moviesmela.ui.Favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.TvDetails;

import java.util.ArrayList;
import java.util.List;

public class FavTvListAdapter extends RecyclerView.Adapter<FavTvListAdapter.FavTvListViewHolder> {

    private Context context;
    private List<TvDetails>tvDetailsList = new ArrayList<>();
    private OnFavItemClickListener favItemClickListener;

    public FavTvListAdapter(Context context, OnFavItemClickListener favItemClickListener) {
        this.context = context;
        this.favItemClickListener = favItemClickListener;
    }

    public interface OnFavItemClickListener{
        void onFavItemClick(int position);
    }

    @NonNull
    @Override
    public FavTvListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavTvListViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvListViewHolder holder, int position) {

        if (tvDetailsList != null){
            TvDetails tvDetails = tvDetailsList.get(position);
            String imagePath = context.getResources().getString(R.string.imageUrl_posterpath) + tvDetails.getPosterPath();
            Picasso.get().load(imagePath)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.tmdb)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return tvDetailsList.size();
    }

    public void setTvDetailsList(List<TvDetails> tvDetailsList) {
        this.tvDetailsList = tvDetailsList;
        notifyDataSetChanged();
    }

    public TvDetails getfavTvShow(int position){
        return tvDetailsList.get(position);
    }

    class FavTvListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        public FavTvListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fav_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            favItemClickListener.onFavItemClick(position);
        }
    }
}
