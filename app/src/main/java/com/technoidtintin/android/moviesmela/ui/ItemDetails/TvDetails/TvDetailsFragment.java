package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.Model.SimilarTv;
import com.technoidtintin.android.moviesmela.Model.SimilarTvResults;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.databinding.ActivityItemDetailsBinding;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvDetailsFragment extends Fragment {

    private String TAG = TvDetailsFragment.class.getSimpleName();
    private ActivityItemDetailsBinding itemDetailsBinding;
    private TvDetailsViewModel tvDetailsViewModel;

    private int itemId;
    private String apiKey;
    private String mediaType;
    private String backDrop;
    private String poster;
    private ListItem listItem;
    private AlertDialog loadingDialog;

    private TvSeasonAdapter seasonAdapter;

    private SimilarTvAdapter similarTvAdapter;

    public TvDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Initializing DataBinding class
        itemDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.activity_item_details, container, false);

        //Initializing ViewModel class
        tvDetailsViewModel = ViewModelProviders.of(this).get(TvDetailsViewModel.class);

        //Initializing ApiKey
        apiKey = getResources().getString(R.string.api_key);

        //Setting up toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(itemDetailsBinding.detailsToolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        itemDetailsBinding.detailsToolbar.setTitle("");
        itemDetailsBinding.detailCollapsingToolBar.setTitleEnabled(true);
        itemDetailsBinding.detailCollapsingToolBar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutExpandedTextStyle);
        itemDetailsBinding.detailCollapsingToolBar.setCollapsedTitleTextColor(Color.WHITE);

        //Setting up AppBar Collapsing Behaviour
        itemDetailsBinding.detailAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    itemDetailsBinding.titleImageLayout.setVisibility(View.GONE);
                    itemDetailsBinding.detailsToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    //Expanded
                    itemDetailsBinding.titleImageLayout.setVisibility(View.VISIBLE);
                    itemDetailsBinding.detailsToolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        //Getting List item from Activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity) getActivity();
        listItem = itemDetailsActivity.getListItem();

        String posterImage = listItem.getMoviePosterPath();
        Picasso.get().load(posterImage).into(itemDetailsBinding.itemImageView);
        itemId = listItem.getId();

        //Create loading Dialog
        loadingDialog = createLoadingDialog(getContext());
        loadingDialog.show();

        //Get Tv Shows Details

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getTvShowsDetails(itemId);
            }
        },4000);

        //Setting RecyclerView
        itemDetailsBinding.detailScrolling.detailsRecycler.setHasFixedSize(true);
        itemDetailsBinding.detailScrolling.detailsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        seasonAdapter = new TvSeasonAdapter(getContext());
        itemDetailsBinding.detailScrolling.detailsRecycler.setAdapter(seasonAdapter);

        //Get Similar Tv Shows
        //Setting Similar Tv Shows recyclerView
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setHasFixedSize(true);
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        similarTvAdapter = new SimilarTvAdapter(getContext());
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setAdapter(similarTvAdapter);

        getSimilarTvShows(itemId, apiKey);
        return itemDetailsBinding.getRoot();
    }

    //Get Tv Shows Details
    private void getTvShowsDetails(int tvId) {
        tvDetailsViewModel.getTvShowsDetails(tvId, apiKey).observe(this, new Observer<TvDetails>() {
            @Override
            public void onChanged(TvDetails tvDetails) {
                loadingDialog.dismiss();
                if (tvDetails != null) {
                    Log.e(TAG, "TV Details is full");
                    setTvDetailsLayout(tvDetails);
                } else {
                    Log.e(TAG, "TV Details is null");
                }
            }
        });
    }

    //Get Similar Tv Shows
    private void getSimilarTvShows(int id, String apiKey) {

        Log.e(TAG, "id: " + id + ", apikey: " + apiKey);
        tvDetailsViewModel.getSimilarTVShows(id, apiKey).observe(this, new Observer<SimilarTv>() {
            @Override
            public void onChanged(SimilarTv similarTv) {

                if (similarTv != null) {
                    Log.e(TAG, "Similar Tv list is full : " + similarTv.getPage());
                    List<SimilarTvResults> similarTvResults = similarTv.getResults();
                    if (similarTvResults != null) {
                        similarTvAdapter.setSimilarTvResultsList(similarTvResults);
                    } else {
                        Log.e(TAG, "Similar Tv list is empty");
                    }
                } else {
                    Log.e(TAG, "Similar Tv list is empty");
                }
            }
        });
    }

    //Set TvDetails Layout
    private void setTvDetailsLayout(TvDetails tvDetails) {

        List<Season> seasonList = tvDetails.getSeasons();
        seasonAdapter.setSeasonList(seasonList);

        poster = getResources().getString(R.string.imageUrl_posterpath_small) + tvDetails.getPosterPath();
        backDrop = getResources().getString(R.string.backdrop_url) + tvDetails.getBackdropPath();
        String rating = String.valueOf(tvDetails.getVoteAverage());
        itemDetailsBinding.setTvDetails(tvDetails);
        itemDetailsBinding.detailCollapsingToolBar.setTitle(tvDetails.getName());
        itemDetailsBinding.detailScrolling.itemReleasedateTvLabel.setText("Last Air Date");
        itemDetailsBinding.detailScrolling.ratingTv.setText(rating + "/10");
        Picasso.get().load(backDrop).fit().centerCrop().into(itemDetailsBinding.itemBannerIv);
    }

    //Create Loading Dialog
    private AlertDialog createLoadingDialog(Context context) {
        View view = getLayoutInflater().inflate(R.layout.loading_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.TransparentDialog);
        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }
}
