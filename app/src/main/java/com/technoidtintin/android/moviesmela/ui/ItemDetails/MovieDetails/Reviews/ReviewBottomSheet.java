package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.Reviews;


import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.technoidtintin.android.moviesmela.Keys;
import com.technoidtintin.android.moviesmela.MovieReviews;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.ReViewsList;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewBottomSheet extends BottomSheetDialogFragment {


    private BottomSheetBehavior bottomSheetBehavior;
    private ReviewAdapter reviewAdapter;
    private ReViewBottomViewModel reViewBottomViewModel;
    private static final String TAG = ReviewBottomSheet.class.getSimpleName();
    private int movie_id;
    private String apiKey;
    private LottieAnimationView reviewLoading;
    private RecyclerView reviewRecyclerView;

    public ReviewBottomSheet() {
        // Required empty public constructor
    }

    public ReviewBottomSheet(int movie_id) {
        this.movie_id = movie_id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog reviewSheetDialog =(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_review_bottom_sheet,null);

        ConstraintLayout rootLayout = view.findViewById(R.id.root_layout);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)rootLayout.getLayoutParams();
        layoutParams.height = getScreenHeight();
        rootLayout.setLayoutParams(layoutParams);

        //Initializing ViewModel class
        reViewBottomViewModel = ViewModelProviders.of(this).get(ReViewBottomViewModel.class);

        //Initialize api key
        apiKey = new Keys().getKey();

        //Initializing review loading
        reviewLoading = view.findViewById(R.id.review_loading);

        //Setting Up ReView RecyclerView
        reviewRecyclerView = view.findViewById(R.id.movie_review_recyclerView);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Initializing ReviewAdapter
        reviewAdapter = new ReviewAdapter(getContext());

        //Attaching adapter with recylerView
        reviewRecyclerView.setAdapter(reviewAdapter);

        //Getting Reviews
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMovieReview(movie_id,apiKey);
            }
        },3000);

        ImageView imageView = view.findViewById(R.id.close_review_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        reviewSheetDialog.setContentView(view);
        bottomSheetBehavior = BottomSheetBehavior.from((View)view.getParent());
        return reviewSheetDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    //Get Movie Reviews
    private void getMovieReview(int moive_id, String apiKey){

        reViewBottomViewModel.getMovieReviews(moive_id, apiKey).observe(this, new Observer<ReViewsList>() {
            @Override
            public void onChanged(ReViewsList reViewsList) {
                reviewLoading.setVisibility(View.GONE);
                reviewRecyclerView.setVisibility(View.VISIBLE);
                if (reViewsList != null){
                    Log.e(TAG,"Movie Review list is full");
                    List<MovieReviews>movieReviewsList = reViewsList.getResults();
                    reviewAdapter.setMovieReviewsList(movieReviewsList);
                }else {
                    Log.e(TAG,"Movie Review list is null");
                }
            }
        });
    }

    //Get Screen Height
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
