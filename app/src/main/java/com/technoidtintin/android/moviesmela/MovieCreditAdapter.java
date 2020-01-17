package com.technoidtintin.android.moviesmela;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieCreditAdapter extends RecyclerView.Adapter<MovieCreditAdapter.MovieCreditViewHolder>{

    private Context context;
    private List<MovieCast>movieCastList = new ArrayList<>();

    public MovieCreditAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieCreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieCreditViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_cast_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCreditViewHolder holder, int position) {

        MovieCast movieCast = movieCastList.get(position);
        String posterImage = context.getResources().getString(R.string.imageUrl_posterpath) + movieCast.getProfilePath();
        Picasso.get().load(posterImage).placeholder(R.drawable.tmdb).error(R.drawable.tmdb).into(holder.movieCastImage);
    }

    @Override
    public int getItemCount() {
        return movieCastList.size();
    }

    public void setMovieCastList(List<MovieCast> movieCastList) {
        this.movieCastList = movieCastList;
        notifyDataSetChanged();
    }

    class MovieCreditViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieCastImage;

        public MovieCreditViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCastImage = itemView.findViewById(R.id.movie_cast_iv);
        }
    }
}
