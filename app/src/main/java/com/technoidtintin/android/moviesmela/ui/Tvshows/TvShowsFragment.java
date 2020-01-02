package com.technoidtintin.android.moviesmela.ui.Tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.technoidtintin.android.moviesmela.R;

public class TvShowsFragment extends Fragment {

    private TvShowsViewModel tvShowsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tvShowsViewModel =
                ViewModelProviders.of(this).get(TvShowsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tvshows, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        tvShowsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}