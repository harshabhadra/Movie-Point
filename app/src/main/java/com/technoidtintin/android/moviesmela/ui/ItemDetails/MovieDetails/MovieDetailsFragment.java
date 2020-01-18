package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.technoidtintin.android.moviesmela.Model.MovieCast;
import com.technoidtintin.android.moviesmela.Model.MovieCredits;
import com.technoidtintin.android.moviesmela.Model.SimilarMovieResults;
import com.technoidtintin.android.moviesmela.Model.SimilarMovies;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.Reviews.ReviewBottomSheet;
import com.technoidtintin.android.moviesmela.databinding.FragmentMovieDetailsBinding;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment implements SimilarMoviesAdapter.OnSimilarMovieItemClickListner{

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    private FragmentMovieDetailsBinding movieDetailsBinding;
    private MovieDetailsViewModel movieDetailsViewModel;

    private ListItem listItem;

    private String posterPath;
    private String apiKey;
    private int movieId;

    private AlertDialog loadingDialog;

    private MovieCreditAdapter movieCreditAdapter;
    private SimilarMoviesAdapter similarMoviesAdapter;
    public OnSimilarMovieClickListener similarMovieClickListener;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public interface OnSimilarMovieClickListener{
        void onSimilarMovieClick(SimilarMovieResults similarMovieResults);
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

        //Initializing Api key
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

        //Getting Movie credits
        getMovieCast(movieId,apiKey);

        //Initialize Similar Movies Adapter
        movieDetailsBinding.movieDetailsScrolling.similarMoviesRecycler
                .setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        similarMoviesAdapter = new SimilarMoviesAdapter(getContext(),MovieDetailsFragment.this);
        movieDetailsBinding.movieDetailsScrolling.similarMoviesRecycler.setAdapter(similarMoviesAdapter);

        //Getting Similar Movies
        getSimilarMovies(movieId,apiKey);

        //Set on click listener to review button
        movieDetailsBinding.movieDetailsScrolling.reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReviewBottomSheet reviewBottomSheet = new ReviewBottomSheet(movieId);
                reviewBottomSheet.show(getFragmentManager(),reviewBottomSheet.getTag());
            }
        });

        return movieDetailsBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //Getting the Movie Item from Activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity)getActivity();
        listItem = itemDetailsActivity.getListItem();

        //Initialize movie id and api key
        movieId = listItem.getId();

        similarMovieClickListener = (OnSimilarMovieClickListener)context;
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

    //Get Similar Movies
    private void getSimilarMovies(int id, String key){

        movieDetailsViewModel.getSimilarMovies(id, key).observe(this, new Observer<SimilarMovies>() {
            @Override
            public void onChanged(SimilarMovies similarMovies) {

                if (similarMovies != null){
                    Log.e(TAG,"Similar Movies list is full");
                    List<SimilarMovieResults>similarMovieResultsList = similarMovies.getResults();
                    similarMoviesAdapter.setSimilarMovieResultsList(similarMovieResultsList);
                }else {
                    Log.e(TAG,"Similar Movies list is null");
                }
            }
        });
    }

    //Set Up Movie Details layout
    private void setUpMovieDetailsLayout(Movies movies){

        movieDetailsBinding.setMovieDetails(movies);
        String backdropPath = getResources().getString(R.string.backdrop_url) + movies.getBackdropPath();
        String posterPath = getResources().getString(R.string.imageUrl_posterpath) + movies.getPosterPath();

        //load the Movie Poster
        Picasso.get().load(posterPath)
                .error(R.drawable.tmdb)
                .into(movieDetailsBinding.movieItemImageView);

        //Load the Backdrop
        Picasso.get().load(backdropPath)
                .fit()
                .centerCrop()
                .into(movieDetailsBinding.movieItemBannerIv);
        movieDetailsBinding.movieDetailCollapsingToolBar.setTitle(movies.getTitle());
    }

    //Create Loading Dialog
    private AlertDialog createLoadingDialog(Context context){
        View layout = getLayoutInflater().inflate(R.layout.loading_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.TransparentDialog);
        builder.setView(layout);
        builder.setCancelable(false);
        return builder.create();
    }

    @Override
    public void onSimilarMovieItemClick(int position) {

        SimilarMovieResults similarMovieResults = similarMoviesAdapter.getSimilarMovie(position);
        similarMovieClickListener.onSimilarMovieClick(similarMovieResults);
    }
}
