package com.technoidtintin.android.moviesmela.ui.Tvshows;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

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
    public void onBindViewHolder(final TvSliderViewHolder viewHolder, int position) {

        if (trendResultList != null){
            String imageUrl = "http://image.tmdb.org/t/p/w1280" + trendResultList.get(position).getBackdropPath();
            Picasso.get().load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.imageView);

            TrendResult trendResult = trendResultList.get(position);
            final ListItem trendItem = getTrendItem(trendResult);

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra(Constant.LIST_ITEM,trendItem);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                            viewHolder.imageView,"item_banner");
                    context.startActivity(intent,options.toBundle());
                }
            });
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

    //Get Trend Item from Trend Result
    private ListItem getTrendItem(TrendResult trendResultList) {


        int id = trendResultList.getId();
        String title = trendResultList.getOriginalTitle();
        String image = trendResultList.getPosterPath();
        String imageUrl = context.getResources().getString(R.string.imageUrl_posterpath) + image;

        if (trendResultList.getMediaType().equals(Constant.MOVIE)) {
            ListItem listItem = new ListItem(id, Constant.MOVIE_TYPE, title, imageUrl);
            return listItem;
        }else {
            ListItem listItem = new ListItem(id, Constant.TV_TYPE, title, imageUrl);
            return listItem;
        }
    }
}
