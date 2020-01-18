package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.SimilarMovieResults;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesViewHolder>{

    private Context context;
    private List<SimilarMovieResults> similarMovieResultsList = new ArrayList<>();
    public OnSimilarMovieItemClickListner similarMovieItemClickListner;

    public interface OnSimilarMovieItemClickListner{

        void onSimilarMovieItemClick(int position);
    }

    public SimilarMoviesAdapter(Context context, OnSimilarMovieItemClickListner similarMovieItemClickListner) {
        this.context = context;
        this.similarMovieItemClickListner = similarMovieItemClickListner;
    }

    @NonNull
    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMoviesViewHolder holder, int position) {

        SimilarMovieResults results = similarMovieResultsList.get(position);
        String posterImage = context.getResources().getString(R.string.imageUrl_posterpath) + results.posterPath;
        Picasso.get().load(posterImage).placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .into(holder.movieIv);
    }

    @Override
    public int getItemCount() {
        return similarMovieResultsList.size();
    }

    //Set Similar Movie list
    public void setSimilarMovieResultsList(List<SimilarMovieResults> similarMovieResultsList) {
        this.similarMovieResultsList = similarMovieResultsList;
        notifyDataSetChanged();
    }

    //Get Similar Movie
    public SimilarMovieResults getSimilarMovie(int position){
        return similarMovieResultsList.get(position);
    }

    class SimilarMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieIv;

        public SimilarMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
        movieIv = itemView.findViewById(R.id.similar_movie_iv);
        itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            similarMovieItemClickListner.onSimilarMovieItemClick(adapterPosition);
        }
    }
}
