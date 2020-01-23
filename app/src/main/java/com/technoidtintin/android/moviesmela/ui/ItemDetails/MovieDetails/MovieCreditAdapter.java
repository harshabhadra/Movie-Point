package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.MovieCast;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.MovieCastItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieCreditAdapter extends RecyclerView.Adapter<MovieCreditAdapter.MovieCreditViewHolder>{

    private Context context;
    private List<MovieCast>movieCastList = new ArrayList<>();
    private OnMovieCastItemClickListener movieCastItemClickListener;
    private boolean isVisible = false;

    public MovieCreditAdapter(Context context, OnMovieCastItemClickListener movieCastItemClickListener) {
        this.context = context;
        this.movieCastItemClickListener = movieCastItemClickListener;
    }

    public interface OnMovieCastItemClickListener{
        void onMoiveCastItemClick(int position);
    }

    @NonNull
    @Override
    public MovieCreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieCastItemBinding movieCastItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context)
                ,R.layout.movie_cast_item,parent,false );
        return new MovieCreditViewHolder(movieCastItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieCreditViewHolder holder, int position) {

        MovieCast movieCast = movieCastList.get(position);
        String posterImage = context.getResources().getString(R.string.imageUrl_posterpath) + movieCast.getProfilePath();
        holder.movieCastItemBinding.setMoviecast(movieCast);
        Picasso.get().load(posterImage)
                .placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .into(holder.movieCastItemBinding.movieCastIv);

        holder.movieCastItemBinding.movieInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isVisible){
                    holder.movieCastItemBinding.castNameGroup.setVisibility(View.VISIBLE);
                    isVisible = true;
                }else {
                    holder.movieCastItemBinding.castNameGroup.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCastList.size();
    }

    public void setMovieCastList(List<MovieCast> movieCastList) {
        this.movieCastList = movieCastList;
        notifyDataSetChanged();
    }

    //GEt Movie cast item
    public MovieCast getMovieCast(int position){
        return movieCastList.get(position);
    }

    class MovieCreditViewHolder extends RecyclerView.ViewHolder {


        private MovieCastItemBinding movieCastItemBinding;

        public MovieCreditViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCastItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
