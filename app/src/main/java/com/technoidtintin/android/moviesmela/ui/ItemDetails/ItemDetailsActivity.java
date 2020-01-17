package com.technoidtintin.android.moviesmela.ui.ItemDetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.MovieDetailsFragment;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails.TvDetailsFragment;

public class ItemDetailsActivity extends AppCompatActivity {

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

    public ListItem getListItem() {
        return listItem;
    }
}
