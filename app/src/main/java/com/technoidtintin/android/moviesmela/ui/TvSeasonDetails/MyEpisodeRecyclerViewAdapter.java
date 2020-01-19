package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Episode;
import com.technoidtintin.android.moviesmela.ui.TvSeasonDetails.EpisodeFragment.OnListFragmentInteractionListener;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEpisodeRecyclerViewAdapter extends RecyclerView.Adapter<MyEpisodeRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Episode> episodeList = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;
    private static final String TAG = MyEpisodeRecyclerViewAdapter.class.getSimpleName();

    public MyEpisodeRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {

        this.context = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Episode episode = episodeList.get(position);

        String imageUrl = context.getResources().getString(R.string.imageUrl_still_path_original) + episode.getStillPath();
        Log.e(TAG,"Image : " + imageUrl);

        String titletext = "S" + episode.seasonNumber + " E" + episode.episodeNumber + ": " + episode.name;
        holder.episodeTitle.setText(titletext);
        Picasso.get().load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.tmdb)
                .into(holder.episodeImage);

        holder.episodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(episode);
                }
            }
        });
    }

    public void setEpisodeList(List<Episode> episodeList) {
        if (!(this.episodeList.size() == 0)){
            this.episodeList.clear();
        }else {
            this.episodeList = episodeList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView episodeImage;
        private TextView episodeTitle;

        public ViewHolder(View view) {
            super(view);

            episodeTitle = view.findViewById(R.id.episode_title);
            episodeImage = view.findViewById(R.id.episode_iv);
        }

    }
}
