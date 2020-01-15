package com.technoidtintin.android.moviesmela;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.technoidtintin.android.moviesmela.ui.Favorite.FavoriteFragment;
import com.technoidtintin.android.moviesmela.ui.Movies.MovieFragment;
import com.technoidtintin.android.moviesmela.ui.Tvshows.TvShowsFragment;
import com.technoidtintin.android.moviesmela.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    MovieFragment movieFragment = new MovieFragment();
    TvShowsFragment tvShowsFragment = new TvShowsFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment active = homeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.navigation_home :
                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.navigation_movies:
                    fragmentManager.beginTransaction().hide(active).show(movieFragment).commit();
                    active = movieFragment;
                    return true;
                case R.id.navigation_tv_shows:
                    fragmentManager.beginTransaction().hide(active).show(tvShowsFragment).commit();
                    active = tvShowsFragment;
                    return true;
                case R.id.navigation_favorites:
                    fragmentManager.beginTransaction().hide(active).show(favoriteFragment).commit();
                    active = favoriteFragment;
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.nav_host_fragment,favoriteFragment,"4").hide(favoriteFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment,tvShowsFragment,"3").hide(tvShowsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment,movieFragment,"2").hide(movieFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment,homeFragment,"1").commit();
    }

}
