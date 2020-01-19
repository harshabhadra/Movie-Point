package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Episode;
import com.technoidtintin.android.moviesmela.EpisodeBottomSheet;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;

public class SeasonDetailsActivity extends AppCompatActivity implements EpisodeFragment.OnListFragmentInteractionListener {

    private ViewPager2 seasonViewPager;
    private int tv_id, seasonNo;
    private boolean isContainZero;
    private static final String TAG = SeasonDetailsActivity.class.getSimpleName();
    private String tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_details);

        //Getting Intent
        Intent intent = getIntent();
        final ArrayList<Season>seasonArrayList = intent.getParcelableArrayListExtra(Constant.SEASON_LIST);
        ArrayList<Integer>seasonUoList = getSeasonNoList(seasonArrayList);
        tvTitle = intent.getStringExtra(Constant.TV_TITLE);

        //Initializing Toolbar
        Toolbar toolbar = findViewById(R.id.episode_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setTitle(tvTitle);
        toolbar.setTitleTextColor(Color.WHITE);

        tv_id = intent.getIntExtra(Constant.TV_ID,-1);
        seasonNo = intent.getIntExtra(Constant.SEASON_NO,-1);
        Log.e(TAG,"Tv id: " + tv_id + " Season No. : " + seasonNo );

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        seasonViewPager = findViewById(R.id.season_viewPager);

        if (seasonUoList.contains(0)){

            isContainZero = true;
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,seasonArrayList,tv_id,seasonNo, isContainZero);
            seasonViewPager.setAdapter(viewPagerAdapter);
            seasonViewPager.setCurrentItem(seasonNo);
        }else {

            isContainZero = false;
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,seasonArrayList,tv_id,seasonNo, isContainZero);
            seasonViewPager.setAdapter(viewPagerAdapter);
            seasonViewPager.setCurrentItem(seasonNo-1);
        }

        new TabLayoutMediator(tabLayout, seasonViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (isContainZero){
                    tab.setText("Season " + position);
                }else {
                    tab.setText("Season " + (position + 1));
                }
            }
        }).attach();
    }

    @Override
    public void onListFragmentInteraction(Episode item) {

        EpisodeBottomSheet episodeBottomSheet = new EpisodeBottomSheet(item);
        episodeBottomSheet.show(getSupportFragmentManager(),episodeBottomSheet.getTag());
    }

    //Get Season no list
    private ArrayList<Integer> getSeasonNoList(ArrayList<Season>seasonArrayList){

        ArrayList<Integer>integerArrayList = new ArrayList<>();
        for (int i = 0 ; i <seasonArrayList.size(); i++){

            int seasonNO = seasonArrayList.get(i).getSeasonNumber();
            integerArrayList.add(seasonNO);
        }
        return integerArrayList;
    }
}
