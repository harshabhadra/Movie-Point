package com.technoidtintin.android.moviesmela.ui.Tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.technoidtintin.android.moviesmela.Adapters.SliderAdapter;
import com.technoidtintin.android.moviesmela.Adapters.TvAdapter;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentTvshowsBinding;

public class TvShowsFragment extends Fragment {

    private TvShowsViewModel tvShowsViewModel;
    private FragmentTvshowsBinding tvshowsBinding;
    private SliderAdapter sliderAdapter;
    private TvAdapter tvAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tvShowsViewModel =
                ViewModelProviders.of(this).get(TvShowsViewModel.class);
        tvshowsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_tvshows,container,false);

        //Initializing SliderView
        sliderAdapter = new SliderAdapter(getContext());
        tvshowsBinding.tvSliderView.setSliderAdapter(sliderAdapter);
        tvshowsBinding.tvSliderView.setScrollTimeInSec(6);

        //Setting up Tv recyclerView
        tvshowsBinding.tvRecycler.setHasFixedSize(true);
        tvshowsBinding.tvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return tvshowsBinding.getRoot();
    }
}