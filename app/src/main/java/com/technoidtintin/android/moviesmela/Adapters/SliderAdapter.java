package com.technoidtintin.android.moviesmela.Adapters;

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

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private Context context;
    private List<TrendResult>trendResultList = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.slider_item,parent,false));
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        if (trendResultList != null){
            viewHolder.textViewDescription.setText(trendResultList.get(position).getName());
            String imageUrl = "http://image.tmdb.org/t/p/w500" + trendResultList.get(position).getBackdropPath();
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

    class SliderViewHolder extends SliderViewAdapter.ViewHolder{

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);
            textViewDescription = itemView.findViewById(R.id.slider_text);
        }
    }
}