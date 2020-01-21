package com.technoidtintin.android.moviesmela.ui.Favorite;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Movies;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.Utility;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

import java.util.List;

public class FavoriteFragment extends Fragment implements
        FavMovieListAdapter.OnFavMovieItemClickListener, FavTvListAdapter.OnFavItemClickListener {

    private static final String FRAGMENT_POSITION = "fragment_position";
    private int position;
    private RecyclerView favMovieRecyclerView;
    private RecyclerView favTvRecyclerView;

    private FavMovieListAdapter favMovieListAdapter;
    private FavTvListAdapter favTvListAdapter;

    private MovieFavViewModel movieFavViewModel;
    private TvFavViewModel tvFavViewModel;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance(int position) {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(FRAGMENT_POSITION, position);
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, container, false);

        //Initialize the ViewModel Class
        movieFavViewModel = ViewModelProviders.of(this).get(MovieFavViewModel.class);
        tvFavViewModel = ViewModelProviders.of(this).get(TvFavViewModel.class);

        favMovieRecyclerView = view.findViewById(R.id.fav_recyclerView);
        favTvRecyclerView = view.findViewById(R.id.fav_tv_recyclerView);

        int spanCount = Utility.calculateNoOfColumns(getContext(), 140);

        //Setting up reyclerView
        favMovieRecyclerView.setHasFixedSize(true);
        favMovieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        favMovieListAdapter = new FavMovieListAdapter(getContext(), FavoriteFragment.this);
        favMovieRecyclerView.setAdapter(favMovieListAdapter);

        favTvRecyclerView.setHasFixedSize(true);
        favTvRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        favTvListAdapter = new FavTvListAdapter(getContext(), FavoriteFragment.this);
        favTvRecyclerView.setAdapter(favTvListAdapter);


        if (position == 0) {
            favMovieRecyclerView.setVisibility(View.VISIBLE);
            favTvRecyclerView.setVisibility(View.GONE);
            getFavMovieList();
        } else {
            favMovieRecyclerView.setVisibility(View.GONE);
            favTvRecyclerView.setVisibility(View.VISIBLE);
            getFavTvShows();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            position = getArguments().getInt(FRAGMENT_POSITION);
        }
    }

    //Get Fav Movie List
    private void getFavMovieList() {

        movieFavViewModel.getFavMovieList().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {

                if (movies != null) {
                    favMovieListAdapter.setMoviesList(movies);
                }
            }
        });
    }

    //Get Fav Tv Shows
    private void getFavTvShows() {
        tvFavViewModel.getFavTvShowList().observe(this, new Observer<List<TvDetails>>() {
            @Override
            public void onChanged(List<TvDetails> tvDetails) {

                if (tvDetails != null) {
                    favTvListAdapter.setTvDetailsList(tvDetails);
                }
            }
        });
    }

    @Override
    public void onFavMovieItemClick(int position) {

        Movies movies = favMovieListAdapter.getFavMovies(position);
        createChooserForMovie(movies);
    }

    @Override
    public void onFavItemClick(int position) {

        TvDetails tvDetails = favTvListAdapter.getfavTvShow(position);
        createChooserForTv(tvDetails);
    }

    //Create Alert Dialgo to delete or see details
    private void createChooserForMovie(final Movies movies) {

        View view = getLayoutInflater().inflate(R.layout.choose_layout, null);

        MaterialButton deleteTv = view.findViewById(R.id.fav_delete);
        MaterialButton detailsTv = view.findViewById(R.id.fav_see_details);
        ImageView closeImage = view.findViewById(R.id.close_chooser_iv);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieFavViewModel.deleteMovieFromFav(movies);
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.putExtra(Constant.FAV_MOVIE, "fav_movie");
                startActivity(intent);
                getActivity().finish();
            }
        });

        detailsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem listItem = convertMoviesToList(movies);
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
                intent.putExtra(Constant.LIST_ITEM, listItem);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    //Create Chooser for TvShow
    private void createChooserForTv(final TvDetails tvDetails) {
        View view = getLayoutInflater().inflate(R.layout.choose_layout, null);

        MaterialButton deleteTv = view.findViewById(R.id.fav_delete);
        MaterialButton detailsTv = view.findViewById(R.id.fav_see_details);
        ImageView closeImage = view.findViewById(R.id.close_chooser_iv);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFavViewModel.deleteTvShowsFromFav(tvDetails);
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.putExtra(Constant.FAV_TV, "fav_movie");
                startActivity(intent);
                getActivity().finish();
            }
        });

        detailsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem listItem = convertTvToListItem(tvDetails);
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
                intent.putExtra(Constant.LIST_ITEM, listItem);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    //Convert Movies item to List item
    private ListItem convertMoviesToList(Movies movies) {

        return new ListItem(movies.getId(), Constant.MOVIE_TYPE, movies.getTitle(), movies.getPosterPath());
    }

    //Convert TvDetails item to ListItem
    private ListItem convertTvToListItem(TvDetails tvDetails) {
        return new ListItem(tvDetails.getId(), Constant.TV_TYPE, tvDetails.getName(), tvDetails.getPosterPath());
    }
}
