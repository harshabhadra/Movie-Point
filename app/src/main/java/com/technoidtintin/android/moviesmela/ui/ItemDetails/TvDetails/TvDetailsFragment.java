package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.technoidtintin.android.moviesmela.TvCast;
import com.technoidtintin.android.moviesmela.TvCredits;
import com.technoidtintin.android.moviesmela.TvDetails;
import com.technoidtintin.android.moviesmela.databinding.ActivityItemDetailsBinding;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvDetailsFragment extends Fragment implements View.OnClickListener,
        SimilarTvAdapter.OnSimilarTvItemClickListener, TvSeasonAdapter.OnSeasonItemClickListener {

    private String TAG = TvDetailsFragment.class.getSimpleName();
    private ActivityItemDetailsBinding itemDetailsBinding;
    private TvDetailsViewModel tvDetailsViewModel;

    private int itemId;
    private String apiKey;
    private String backDrop;
    private boolean castListVisisble = false;
    private ListItem listItem;
    private AlertDialog loadingDialog;

    private TvSeasonAdapter seasonAdapter;

    private SimilarTvAdapter similarTvAdapter;

    private TvCastAdapters castAdapters;

    private OnSimilarTvClickListener similarTvClickListener;

    private OnSeasonClickListener seasonClickListener;

    public TvDetailsFragment() {
        // Required empty public constructor
    }

    //Interface for Similar Tv Item Click
    public interface OnSimilarTvClickListener{
        void onSimilarTvClick(SimilarTvResults similarTvResults);
    }

    //Interface for Season item click
    public interface OnSeasonClickListener{
        void onSeasonClick(List<Season>seasonList, int seasonNo,int tv_id);
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
                    itemDetailsBinding.itemImageView.setVisibility(View.GONE);
                    itemDetailsBinding.detailsToolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDark));
                } else {
                    //Expanded
                    itemDetailsBinding.itemImageView.setVisibility(View.VISIBLE);
                    itemDetailsBinding.detailsToolbar.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        //Getting List item from Activity
        ItemDetailsActivity itemDetailsActivity = (ItemDetailsActivity) getActivity();
        listItem = itemDetailsActivity.getListItem();

        itemId = listItem.getId();

        //Create loading Dialog
        loadingDialog = createLoadingDialog(getContext());
        loadingDialog.show();

        //Get Tv Shows Details
        getTvShowsDetails(itemId);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },4000);

        //Setting RecyclerView
        itemDetailsBinding.detailScrolling.detailsRecycler.setHasFixedSize(true);
        itemDetailsBinding.detailScrolling.detailsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        seasonAdapter = new TvSeasonAdapter(getContext(), TvDetailsFragment.this);
        itemDetailsBinding.detailScrolling.detailsRecycler.setAdapter(seasonAdapter);

        //Get Similar Tv Shows
        //Setting Similar Tv Shows recyclerView
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setHasFixedSize(true);
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        similarTvAdapter = new SimilarTvAdapter(getContext(), TvDetailsFragment.this);
        itemDetailsBinding.detailScrolling.similarTvRecyclerView.setAdapter(similarTvAdapter);
        getSimilarTvShows(itemId, apiKey);

        //Get Tv Shows cast list and set up the Cast RecyclerView
        itemDetailsBinding.detailScrolling.tvCastRecycler.setHasFixedSize(true);
        itemDetailsBinding.detailScrolling.tvCastRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        //Initializing Cast Adapters
        castAdapters = new TvCastAdapters(getContext());
        itemDetailsBinding.detailScrolling.tvCastRecycler.setAdapter(castAdapters);
        getTvCastDetails(itemId);

        itemDetailsBinding.detailScrolling.castMememberLabel.setOnClickListener(this);

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

    //Get Cast Details
    private void getTvCastDetails(int tvid){

        tvDetailsViewModel.getTvCredits(tvid,apiKey).observe(this, new Observer<TvCredits>() {
            @Override
            public void onChanged(TvCredits tvCredits) {

                if (tvCredits != null){
                    Log.e(TAG,"Tv Credits are full");
                    List<TvCast>castList = tvCredits.getCast();
                    castAdapters.setTvCastList(castList);
                }else {
                    Log.e(TAG,"Unable to get Tv Casts");
                }
            }
        });
    }
    //Set TvDetails Layout
    private void setTvDetailsLayout(TvDetails tvDetails) {

        List<Season> seasonList = tvDetails.getSeasons();
        seasonAdapter.setSeasonList(seasonList);

        backDrop = getResources().getString(R.string.backdrop_url) + tvDetails.getBackdropPath();
        String posterPaht = getResources().getString(R.string.imageUrl_posterpath) + tvDetails.getPosterPath();
        itemDetailsBinding.setTvDetails(tvDetails);
        itemDetailsBinding.detailCollapsingToolBar.setTitle(tvDetails.getName());
        Picasso.get()
                .load(backDrop)
                .fit()
                .centerCrop()
                .into(itemDetailsBinding.itemBannerIv);

        Picasso.get()
                .load(posterPaht)
                .error(R.drawable.tmdb)
                .into(itemDetailsBinding.itemImageView);
    }

    //Create Loading Dialog
    private AlertDialog createLoadingDialog(Context context) {
        View view = getLayoutInflater().inflate(R.layout.loading_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.TransparentDialog);
        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View view) {

        if (view.equals(itemDetailsBinding.detailScrolling.castMememberLabel)){
            if (!castListVisisble) {
                itemDetailsBinding.detailScrolling.tvCastRecycler.setVisibility(View.VISIBLE);
                itemDetailsBinding.detailScrolling.castMememberLabel
                        .setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_drop_up_white_24dp,0);
                castListVisisble = true;
            }else {
                itemDetailsBinding.detailScrolling.tvCastRecycler.setVisibility(View.GONE);
                itemDetailsBinding.detailScrolling.castMememberLabel
                        .setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_drop_down_white_24dp,0);

                castListVisisble = false;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        similarTvClickListener = (OnSimilarTvClickListener)context;
        seasonClickListener = (OnSeasonClickListener)context;
    }

    @Override
    public void onSimilarTvItemClick(int position) {

        SimilarTvResults similarTvResults = similarTvAdapter.getSimilarTv(position);
        similarTvClickListener.onSimilarTvClick(similarTvResults);
    }

    @Override
    public void onSeasonItemClick(int position) {

        List<Season>seasonList = seasonAdapter.getSeasonList();
        Season season = seasonAdapter.getSeason(position);
        int seasonNo = season.getSeasonNumber();
        seasonClickListener.onSeasonClick(seasonList,seasonNo,itemId);
    }
}
