package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;

public class SeasonDetailsActivity extends AppCompatActivity {

    private ViewPager2 seasonViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_details);

        //Getting Intent
        Intent intent = getIntent();
        ArrayList<Season>seasonArrayList = intent.getParcelableArrayListExtra(Constant.SEASON_LIST);
        seasonViewPager = findViewById(R.id.season_viewPager);

        seasonViewPager.setAdapter(new SeasonViewPagerAdapter(this,seasonArrayList,seasonViewPager));
    }
}
