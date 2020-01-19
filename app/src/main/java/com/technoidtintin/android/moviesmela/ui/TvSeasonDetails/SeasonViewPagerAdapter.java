package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.technoidtintin.android.moviesmela.Episode;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.SeasonDetails;
import com.technoidtintin.android.moviesmela.SeasonDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SeasonViewPagerAdapter extends RecyclerView.Adapter<SeasonViewPagerAdapter.SeasonViewHolder> {

    private Context context;
    private ArrayList<Season> seasonList;
    private ViewPager2 viewPager2;
    private SeasonDetailsViewModel seasonDetailsViewModel;
    private int tv_id;
    private int seasonNo;
    private String apiKey;
    private EpisodeAdapter episodeAdapter;
    private static final String TAG = SeasonViewPagerAdapter.class.getSimpleName();

    public SeasonViewPagerAdapter(Context context, ArrayList<Season> seasonList, ViewPager2 viewPager2, int tv_id) {
        this.context = context;
        this.seasonList = seasonList;
        this.viewPager2 = viewPager2;
        this.tv_id = tv_id;
        seasonDetailsViewModel = ViewModelProviders.of((SeasonDetailsActivity) context).get(SeasonDetailsViewModel.class);
        apiKey = context.getResources().getString(R.string.api_key);
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeasonViewHolder(LayoutInflater.from(context).inflate(R.layout.episodes_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {

        seasonNo = seasonList.get(position).getSeasonNumber();
        viewPager2.setCurrentItem(seasonNo);
        holder.seasonName.setText(seasonList.get(position).getName());
        holder.episodeRecyler.setHasFixedSize(true);
        holder.episodeRecyler.setLayoutManager(new LinearLayoutManager(context));
        episodeAdapter = new EpisodeAdapter(context);
        holder.episodeRecyler.setAdapter(episodeAdapter);
        getEpisodes(seasonNo);
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder {

        private TextView seasonName;
        private RecyclerView episodeRecyler;

        public SeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            seasonName = itemView.findViewById(R.id.season_name_tv);
            episodeRecyler = itemView.findViewById(R.id.episodes_recycler);
        }
    }

    //Get Episodes
    private void getEpisodes(int season) {
        seasonDetailsViewModel.getSeasonDetails(tv_id, season, apiKey).observe((SeasonDetailsActivity) context, new Observer<SeasonDetails>() {
            @Override
            public void onChanged(SeasonDetails seasonDetails) {

                if (seasonDetails != null) {
                    List<Episode> episodeList = seasonDetails.getEpisodes();
                    Log.e(TAG,"Episodes list is full");
                    episodeAdapter.setEpisodeList(episodeList);
                }else {
                    Log.e(TAG,"Episodes not available");
                    Toast.makeText(context,"Episodes not Available",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
