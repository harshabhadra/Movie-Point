package com.technoidtintin.android.moviesmela.ui.Favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class FavMovieListAdapter extends RecyclerView.Adapter<FavMovieListAdapter.FavListViewHolder> {

    private Context context;
    private List<Movies>moviesList = new ArrayList<>();
    private OnFavMovieItemClickListener favMovieItemClickListener;

    public FavMovieListAdapter(Context context, OnFavMovieItemClickListener favMovieItemClickListener) {
        this.context = context;
        this.favMovieItemClickListener = favMovieItemClickListener;
    }

    public interface OnFavMovieItemClickListener{
        void onFavMovieItemClick(int position);
    }
    @NonNull
    @Override
    public FavListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavListViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavListViewHolder holder, int position) {

        if (moviesList != null){
            Movies movies = moviesList.get(position);
            String posterImage = context.getResources().getString(R.string.imageUrl_posterpath) + movies.getPosterPath();
            Picasso.get().load(posterImage)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.tmdb)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    public Movies getFavMovies(int position){
        return moviesList.get(position);
    }

    class FavListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        public FavListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fav_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            favMovieItemClickListener.onFavMovieItemClick(position);
        }
    }
}
