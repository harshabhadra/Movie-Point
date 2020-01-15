package com.technoidtintin.android.moviesmela.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.ui.ItemDetails.ItemDetailsActivity;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MovieViewHolder> {

    private static final String TAG = ItemListAdapter.class.getSimpleName();
    private List<ListItem> listItemList = new ArrayList<>();
    private Context context;

    public ItemListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"Creting Movie item");
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {

        if (listItemList != null){
            Log.e(TAG,"Movie list not null");
            final ListItem listItem = listItemList.get(position);
            Picasso.get().load(listItem.getMoviePosterPath()).placeholder(R.drawable.tmdb)
                    .error(R.drawable.tmdb)
                    .into(holder.posterImage);
            holder.posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra(Constant.LIST_ITEM,listItem);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context
                            ,holder.posterImage,"item_poster");
                    context.startActivity(intent,options.toBundle());
                }
            });
        }else {
            Log.e(TAG,"Movie list null");
        }

    }

    @Override
    public int getItemCount() {
        return listItemList.size();
    }

    public void setListItemList(List<ListItem> listItemList) {
        this.listItemList = listItemList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.movie_poster_image);
        }
    }
}
