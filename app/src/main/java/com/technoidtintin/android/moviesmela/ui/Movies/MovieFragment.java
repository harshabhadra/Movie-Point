package com.technoidtintin.android.moviesmela.ui.Movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.Adapters.MovieAdapter;
import com.technoidtintin.android.moviesmela.R;

public class MovieFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private MovieAdapter movieAdapter;
    private RecyclerView movieRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        String apiKey = getResources().getString(R.string.api_key);
        moviesViewModel =
                ViewModelProviders.of(this).get(MoviesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_movie, container, false);

        movieRecyclerView =root.findViewById(R.id.movie_recycler);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        movieAdapter = new MovieAdapter(getContext());
        movieRecyclerView.setAdapter(movieAdapter);

        return root;
    }
}