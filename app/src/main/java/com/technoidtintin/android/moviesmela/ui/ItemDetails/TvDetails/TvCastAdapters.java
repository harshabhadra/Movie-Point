package com.technoidtintin.android.moviesmela.ui.ItemDetails.TvDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.TvCast;
import com.technoidtintin.android.moviesmela.databinding.CastLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class TvCastAdapters extends RecyclerView.Adapter<TvCastAdapters.TvCastViewHolder> {

    private Context context;
    private List<TvCast>tvCastList = new ArrayList<>();
    private boolean isVisible = false;

    public TvCastAdapters(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TvCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastLayoutBinding castLayoutBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.cast_layout, parent, false);
        return new TvCastViewHolder(castLayoutBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull final TvCastViewHolder holder, int position) {

        TvCast tvCast = tvCastList.get(position);
        String posterPath = context.getResources().getString(R.string.imageUrl_posterpath) + tvCast.getProfilePath();
        holder.castLayoutBinding.setTvcast(tvCast);
        Picasso.get().load(posterPath)
                .placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .into(holder.castLayoutBinding.castImageView);

        holder.castLayoutBinding.tvCastInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isVisible){
                    holder.castLayoutBinding.tvCastGroup.setVisibility(View.VISIBLE);
                    isVisible = true;
                }else {
                    holder.castLayoutBinding.tvCastGroup.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvCastList.size();
    }

    public void setTvCastList(List<TvCast> tvCastList) {
        this.tvCastList = tvCastList;
        notifyDataSetChanged();
    }

    class TvCastViewHolder extends RecyclerView.ViewHolder{

        private CastLayoutBinding castLayoutBinding;

        public TvCastViewHolder(@NonNull View itemView) {
            super(itemView);

            castLayoutBinding = DataBindingUtil.bind(itemView);
        }
    }
}
