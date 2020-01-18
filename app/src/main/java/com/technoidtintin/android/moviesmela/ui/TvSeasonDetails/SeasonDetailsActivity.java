package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;

public class SeasonDetailsActivity extends AppCompatActivity {

    private ViewPager2 seasonViewPager;
    private int tv_id, seasonNo;
    private static final String TAG = SeasonDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_details);

        //Getting Intent
        Intent intent = getIntent();
        ArrayList<Season>seasonArrayList = intent.getParcelableArrayListExtra(Constant.SEASON_LIST);
        tv_id = intent.getIntExtra(Constant.TV_ID,-1);
        seasonNo = intent.getIntExtra(Constant.SEASON_NO,-1);
        Log.e(TAG,"Tv id: " + tv_id + " Season No. : " + seasonNo );

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        seasonViewPager = findViewById(R.id.season_viewPager);
        
        seasonViewPager.setAdapter(new SeasonViewPagerAdapter(this,seasonArrayList,seasonViewPager,tv_id));
    }
}
