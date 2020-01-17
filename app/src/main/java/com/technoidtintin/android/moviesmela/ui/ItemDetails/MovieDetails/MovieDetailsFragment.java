package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.MovieCast;
import com.technoidtintin.android.moviesmela.MovieCreditAdapter;
import com.technoidtintin.android.moviesmela.MovieCredits;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.FragmentMovieDetailsBinding;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

import java.util.List;


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

    private MovieCreditAdapter movieCreditAdapter;

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

        ((AppCompatActivity)getActivity()).setSupportActionBar(movieDetailsBinding.movieDetailsToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        movieDetailsBinding.movieDetailsToolbar.setTitle("");
        movieDetailsBinding.movieDetailCollapsingToolBar.setTitleEnabled(true);
        movieDetailsBinding.movieDetailCollapsingToolBar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutExpandedTextStyle);
        movieDetailsBinding.movieDetailCollapsingToolBar.setCollapsedTitleTextColor(Color.WHITE);

        //Setting on offset change Listener to the AppBarLayout
        movieDetailsBinding.movieDetailAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                if (Math.abs(i) - appBarLayout.getTotalScrollRange() == 0){
                    //Collapsed
                    movieDetailsBinding.movieItemImageView.setVisibility(View.GONE);
                    movieDetailsBinding.movieDetailsToolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDark));
                }else {
                    movieDetailsBinding.movieItemImageView.setVisibility(View.VISIBLE);
                    movieDetailsBinding.movieDetailsToolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

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

        //Getting Movie Details
        getMovieDetails(movieId,apiKey);

        //Setting Up cast recycler
        movieDetailsBinding.movieDetailsScrolling.movieCastRecycler
                .setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        //Initializing the MovieCredit adapter
        movieCreditAdapter = new MovieCreditAdapter(getContext());
        movieDetailsBinding.movieDetailsScrolling.movieCastRecycler.setAdapter(movieCreditAdapter);
        getMovieCast(movieId,apiKey);

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

    //Get Movie Cast
    private void getMovieCast(int id, String key){
        movieDetailsViewModel.getMovieCredits(id, key).observe(this, new Observer<MovieCredits>() {
            @Override
            public void onChanged(MovieCredits movieCredits) {

                if (movieCredits != null){
                    Log.e(TAG,"Movie Cast list is full: " + movieCredits.getId());
                    List<MovieCast>movieCastList = movieCredits.getCast();
                    movieCreditAdapter.setMovieCastList(movieCastList);
                }else {
                    Log.e(TAG,"Movie Cast list is empty");
                }
            }
        });
    }

    //Set Up Movie Details layout
    private void setUpMovieDetailsLayout(Movies movies){

        movieDetailsBinding.setMovieDetails(movies);
        String backdropPath = getResources().getString(R.string.backdrop_url) + movies.getBackdropPath();
        Picasso.get().load(backdropPath)
                .fit()
                .centerCrop()
                .into(movieDetailsBinding.movieItemBannerIv);
        movieDetailsBinding.movieDetailCollapsingToolBar.setTitle(movies.getTitle());
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
