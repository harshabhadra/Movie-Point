package com.technoidtintin.android.moviesmela;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.databinding.ActivityItemDetailsBinding;

public class ItemDetailsActivity extends AppCompatActivity {

    ActivityItemDetailsBinding itemDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Initializing DataBinding
        itemDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_item_details);
        ImageView imageView = findViewById(R.id.item_imageView);
        ImageView bannerImage = findViewById(R.id.item_banner_iv);
        //Getting Intent
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.LIST_ITEM)) {
            ListItem listItem = intent.getParcelableExtra(Constant.LIST_ITEM);
            Picasso.get().load(listItem.getMoviePosterPath()).into(imageView);
        }else if (intent.hasExtra(Constant.TREND_LIST)){
            TrendResult trendResult = intent.getParcelableExtra(Constant.TREND_LIST);
            String backImageUrl = "http://image.tmdb.org/t/p/w500"+ trendResult.getBackdropPath();
            String posterImage = "http://image.tmdb.org/t/p/w185" + trendResult.getPosterPath();
            Picasso.get().load(backImageUrl).fit().centerCrop().into(bannerImage);
            Picasso.get().load(posterImage)
                    .into(imageView);
            itemDetailsBinding.itemTitle.setText(trendResult.getOriginalName());
            itemDetailsBinding.itemReleasedateTv.setText(trendResult.getFirstAirDate());
        }
    }
}
