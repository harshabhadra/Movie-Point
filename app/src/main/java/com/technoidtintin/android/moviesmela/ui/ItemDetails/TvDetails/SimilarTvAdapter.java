package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.SimilarTvResults;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class SimilarTvAdapter extends RecyclerView.Adapter<SimilarTvAdapter.SimilarTvViewHolder> {

    private Context context;
    private List<SimilarTvResults>similarTvResultsList = new ArrayList<>();
    private OnSimilarTvItemClickListener similarTvItemClickListener;

    public SimilarTvAdapter(Context context, OnSimilarTvItemClickListener similarTvItemClickListener) {
        this.context = context;
        this.similarTvItemClickListener = similarTvItemClickListener;
    }

    public interface OnSimilarTvItemClickListener{
        void onSimilarTvItemClick(int position);
    }

    @NonNull
    @Override
    public SimilarTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarTvViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_tv_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarTvViewHolder holder, int position) {

        if (similarTvResultsList != null){
            SimilarTvResults similarTvResults = similarTvResultsList.get(position);
            String imageUrl = context.getResources().getString(R.string.imageUrl_posterpath_small) + similarTvResults.getPosterPath();
            Picasso.get().load(imageUrl).into(holder.similarImageView);
        }
    }

    @Override
    public int getItemCount() {
        return similarTvResultsList.size();
    }

    //Set Similar Tv list
    public void setSimilarTvResultsList(List<SimilarTvResults> similarTvResultsList) {
        this.similarTvResultsList = similarTvResultsList;
        notifyDataSetChanged();
    }

    //Get Similar Tv list
    public SimilarTvResults getSimilarTv(int position){
        return similarTvResultsList.get(position);
    }

    class SimilarTvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView similarImageView;

        public SimilarTvViewHolder(@NonNull View itemView) {
            super(itemView);

            similarImageView = itemView.findViewById(R.id.similar_tv_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            similarTvItemClickListener.onSimilarTvItemClick(position);
        }
    }
}
