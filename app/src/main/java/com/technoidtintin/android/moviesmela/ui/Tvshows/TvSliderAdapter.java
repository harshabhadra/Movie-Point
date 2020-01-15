package com.technoidtintin.android.moviesmela.ui.Tvshows;

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

public class TvSliderAdapter extends SliderViewAdapter<TvSliderAdapter.TvSliderViewHolder>{

    private Context context;
    private List<TrendResult>trendResultList = new ArrayList<>();

    public TvSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TvSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TvSliderViewHolder(LayoutInflater.from(context).inflate(R.layout.tv_slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TvSliderViewHolder viewHolder, int position) {

        if (trendResultList != null){
            String imageUrl = "http://image.tmdb.org/t/p/w1280" + trendResultList.get(position).getBackdropPath();
            Picasso.get().load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.imageView);
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

    class TvSliderViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public TvSliderViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.tv_slider_image);
        }
    }
}
