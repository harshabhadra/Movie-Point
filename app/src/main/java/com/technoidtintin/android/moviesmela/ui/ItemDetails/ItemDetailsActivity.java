package com.technoidtintin.android.moviesmela.ui.ItemDetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.Model.SimilarMovieResults;
import com.technoidtintin.android.moviesmela.Model.SimilarTvResults;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.ui.TvSeasonDetails.SeasonDetailsActivity;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.MovieDetailsFragment;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails.TvDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity implements
        MovieDetailsFragment.OnSimilarMovieClickListener,
        TvDetailsFragment.OnSimilarTvClickListener, TvDetailsFragment.OnSeasonClickListener{

    private static final String TAG = ItemDetailsActivity.class.getSimpleName();
    private String mediaType;

    private ListItem listItem;
    private TvDetailsFragment tvDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_host_layout);

        //Getting Intent
        tvDetailsFragment = new TvDetailsFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.LIST_ITEM)) {
            listItem = intent.getParcelableExtra(Constant.LIST_ITEM);
            mediaType = listItem.getType();
            if (mediaType.equals(Constant.TV_TYPE)) {
                fragmentTransaction.replace(R.id.details_container, tvDetailsFragment).commit();
            }else if (mediaType.equals(Constant.MOVIE_TYPE)){
                fragmentTransaction.replace(R.id.details_container,new MovieDetailsFragment()).commit();
            }

        }
    }

    //Get List item
    public ListItem getListItem() {
        return listItem;
    }

    //Convert SimilarMovieResults to ListItem
    private ListItem convertSimilarToList(SimilarMovieResults similarMovieResults){

        return new ListItem(similarMovieResults.id,
                Constant.MOVIE_TYPE,
                similarMovieResults.originalTitle,
                similarMovieResults.getPosterPath());
    }

    //convert SimilarTvResults to listitem
    private ListItem convertSimilarTvToList(SimilarTvResults tvResults){

        return new ListItem(tvResults.getId(),
                Constant.TV_TYPE,
                tvResults.getName(),
                tvResults.getPosterPath());
    }

    //Restart the Activity
    private void restartActivity(ListItem restartList){
        Intent intent = new Intent(ItemDetailsActivity.this, ItemDetailsActivity.class);
        intent.putExtra(Constant.LIST_ITEM,restartList);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSimilarMovieClick(SimilarMovieResults similarMovieResults) {

        ListItem similarList = convertSimilarToList(similarMovieResults);
        restartActivity(similarList);
    }

    @Override
    public void onSimilarTvClick(SimilarTvResults similarTvResults) {

        ListItem tvListItem = convertSimilarTvToList(similarTvResults);
        restartActivity(tvListItem);
    }

    @Override
    public void onSeasonClick(List<Season> seasonList, int seasonNo, int tv_id) {

        ArrayList<Season> seasonArrayList = new ArrayList<>(seasonList);
        Intent intent = new Intent(ItemDetailsActivity.this, SeasonDetailsActivity.class);
        intent.putExtra(Constant.SEASON_NO,seasonNo);
        intent.putExtra(Constant.TV_ID,tv_id);
        intent.putExtra(Constant.TV_TITLE, listItem.getMovieTitle());
        intent.putParcelableArrayListExtra(Constant.SEASON_LIST,seasonArrayList);
        startActivity(intent);
    }
}
