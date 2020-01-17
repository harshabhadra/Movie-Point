package com.technoidtintin.android.moviesmela.ui.ItemDetails.MovieDetails.Reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.MovieReviews;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.databinding.ReviewListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context context;
    private List<MovieReviews>movieReviewsList = new ArrayList<>();

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewListItemBinding reviewListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.review_list_item,parent,false);
        return new ReviewViewHolder(reviewListItemBinding.getRoot()) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, int position) {

        MovieReviews movieReviews = movieReviewsList.get(position);
        holder.reviewListItemBinding.setReview(movieReviews);

        holder.reviewListItemBinding.expandableTextView.setInterpolator(new OvershootInterpolator());

        holder.reviewListItemBinding.buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.reviewListItemBinding.expandableTextView.isExpanded()){
                    holder.reviewListItemBinding.expandableTextView.collapse();
                    holder.reviewListItemBinding.buttonToggle.setText("See More . . .");
                    holder.reviewListItemBinding.buttonToggle.
                            setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_expand_more_grey_24dp,0);

                }else {
                    holder.reviewListItemBinding.expandableTextView.expand();
                    holder.reviewListItemBinding.buttonToggle.setText("See less");
                    holder.reviewListItemBinding.buttonToggle.
                            setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_expand_less_grey_24dp,0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieReviewsList.size();
    }

    public void setMovieReviewsList(List<MovieReviews> movieReviewsList) {
        this.movieReviewsList = movieReviewsList;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        ReviewListItemBinding reviewListItemBinding;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewListItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
