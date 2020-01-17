package com.technoidtintin.android.moviesmela;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.SimilarMovieResults;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesViewHolder>{

    private Context context;
    private List<SimilarMovieResults>similarMovieResults = new ArrayList<>();

    public SimilarMoviesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMoviesViewHolder holder, int position) {

        SimilarMovieResults results = similarMovieResults.get(position);
        String posterImage = context.getResources().getString(R.string.imageUrl_posterpath) + results.posterPath;
        Picasso.get().load(posterImage).placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .into(holder.movieIv);
    }

    @Override
    public int getItemCount() {
        return similarMovieResults.size();
    }

    public void setSimilarMovieResults(List<SimilarMovieResults> similarMovieResults) {
        this.similarMovieResults = similarMovieResults;
        notifyDataSetChanged();
    }

    class SimilarMoviesViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieIv;

        public SimilarMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
        movieIv = itemView.findViewById(R.id.similar_movie_iv);
        }
    }
}
