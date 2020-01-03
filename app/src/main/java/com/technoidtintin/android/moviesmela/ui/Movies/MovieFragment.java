package com.technoidtintin.android.moviesmela.ui.Movies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.technoidtintin.android.moviesmela.Adapters.SliderAdapter;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.TrendResult;
import com.technoidtintin.android.moviesmela.Model.Trending;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentMovieBinding;

import java.util.List;

public class MovieFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private FragmentMovieBinding fragmentMovieBinding;
    private SliderAdapter sliderAdapter;

    private static final String TAG = MovieFragment.class.getSimpleName();
    private String apiKey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        apiKey = getResources().getString(R.string.api_key);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        //Initializing DataBinding
        fragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);

        //Initializing Slider Adapter
        sliderAdapter = new SliderAdapter(getContext());
        fragmentMovieBinding.movieSliderView.setSliderAdapter(sliderAdapter);
        fragmentMovieBinding.movieSliderView.setScrollTimeInSec(6);
        getTrendingMoviesToday();

        return fragmentMovieBinding.getRoot();
    }

    //Get list of Trending Today
    private void getTrendingMoviesToday() {

        moviesViewModel.getTrendingMovies(Constant.MOVIE, Constant.DAY,apiKey).observe(this, new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {

                if (trending != null){
                    Log.e(TAG,"Movie Trending list of day is full");
                    List<TrendResult>trendResultList = trending.getResults();
                    sliderAdapter.setTrendResultList(trendResultList);
                }else {
                    Log.e(TAG,"Trending Movie list is empty");
                }
            }
        });
    }
}