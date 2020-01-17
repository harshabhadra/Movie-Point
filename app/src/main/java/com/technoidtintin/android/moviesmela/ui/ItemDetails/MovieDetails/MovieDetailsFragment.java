package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentMovieDetailsBinding;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private FragmentMovieDetailsBinding movieDetailsBinding;
    private MovieDetailsViewModel movieDetailsViewModel;

    private ListItem listItem;

    private String posterPath;
    private String apiKey;
    private int movieId;

    private AlertDialog loadingDialog;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing DataBinding
        movieDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_details,container,false);

        //Initializing MovieDetailsViewModel
        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        //Getting the Movie Item from Activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity)getActivity();
        listItem = itemDetailsActivity.getListItem();

        //Load the Movie Poster
        posterPath = listItem.getMoviePosterPath();
        Picasso.get().load(posterPath).into(movieDetailsBinding.movieItemImageView);

        //Initialize movie id and api key
        movieId = listItem.getId();
        apiKey = getResources().getString(R.string.api_key);

        //Create loading Dialog
        loadingDialog = createLoadingDialog(getContext());
        loadingDialog.show();

        getMovieDetails(movieId,apiKey);

        return movieDetailsBinding.getRoot();
    }

    //Get Movie Details
    private void getMovieDetails(int id, String key){

        movieDetailsViewModel.getMoviesDetails(id, key).observe(this, new Observer<Movies>() {
            @Override
            public void onChanged(Movies movies) {

                loadingDialog.dismiss();
                if (movies != null){
                    Log.e(TAG,"Movie Details is full");
                    setUpMovieDetailsLayout(movies);
                }else {
                    Log.e(TAG,"Movie Details is null");
                }
            }
        });
    }

    //Set Up Movie Details layout
    private void setUpMovieDetailsLayout(Movies movies){

        movieDetailsBinding.setMovieDetails(movies);
        String backdropPath = getResources().getString(R.string.backdrop_url) + movies.getBackdropPath();
        Picasso.get().load(backdropPath).into(movieDetailsBinding.movieItemBannerIv);
    }

    //Create Loading Dialog
    private AlertDialog createLoadingDialog(Context context){
        View layout = getLayoutInflater().inflate(R.layout.loading_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(false);
        return builder.create();
    }
}
