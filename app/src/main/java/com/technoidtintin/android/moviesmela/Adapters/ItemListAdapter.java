package com.technoidtintin.android.moviesmela.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MovieViewHolder> {

    private static final String TAG = ItemListAdapter.class.getSimpleName();
    private List<ListItem> listItemList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ItemListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"Creting Movie item");
        return new MovieViewHolder(layoutInflater.inflate(R.layout.movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if (listItemList != null){
            Log.e(TAG,"Movie list not null");
            ListItem listItem = listItemList.get(position);
            Log.e(TAG,"Title: " + listItem.getMovieTitle() );
            Picasso.get().load(listItem.getMoviePosterPath()).placeholder(R.mipmap.ic_launcher).into(holder.posterImage);
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
