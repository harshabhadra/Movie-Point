package com.technoidtintin.android.moviesmela.ui.Movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class MovieSliderAdapter extends SliderViewAdapter<MovieSliderAdapter.MovieSliderViewHolder> {

    private Context context;
    private List<TrendResult>trendResultList = new ArrayList<>();

    public MovieSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MovieSliderViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MovieSliderViewHolder viewHolder, int position) {

        if (trendResultList != null){
            String imageUrl = "http://image.tmdb.org/t/p/w1280" + trendResultList.get(position).getBackdropPath();
            Picasso.get().load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.imageViewBackground);
        }

    }

    @Override
    public int getCount() {
        return trendResultList.size();
    }

    public void setTrendResultList(List<TrendResult> trendResultList) {
        this.trendResultList = trendResultList;
        notifyDataSetChanged();
    }

    class MovieSliderViewHolder extends SliderViewAdapter.ViewHolder{

        View itemView;
        ImageView imageViewBackground;

        public MovieSliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.movie_slider_image);
        }
    }
}
