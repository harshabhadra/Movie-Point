package com.technoidtintin.android.moviesmela.ui.Favorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.R;

public class FavoriteActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 favViewpager;
    private boolean isMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        tabLayout = findViewById(R.id.fav_tab);
        favViewpager = findViewById(R.id.fav_viewPager);

        Intent intent = getIntent();
        if (intent.hasExtra(Constant.FAV_TV)){
            isMovie = false;
        }else{
            isMovie = true;
        }

        //Initializing FavViewpager class
        FavViewPagerAdapter favViewPagerAdapter = new FavViewPagerAdapter(this);
        favViewpager.setAdapter(favViewPagerAdapter);

        new TabLayoutMediator(tabLayout, favViewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (position == 0){
                    tab.setText("Movies");
                }else {
                    tab.setText("Tv Shows");
                }
            }
        }).attach();
        if (!isMovie){
            favViewpager.setCurrentItem(1);
        }else {
            favViewpager.setCurrentItem(0);
        }
    }
}
