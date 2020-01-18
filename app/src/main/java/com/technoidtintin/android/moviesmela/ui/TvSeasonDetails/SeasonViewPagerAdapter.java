package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;

import java.util.ArrayList;

public class SeasonViewPagerAdapter extends RecyclerView.Adapter<SeasonViewPagerAdapter.SeasonViewHolder>{

    private Context context;
    private ArrayList<Season> seasonList;
    private ViewPager2 viewPager2;

    public SeasonViewPagerAdapter(Context context, ArrayList<Season> seasonList, ViewPager2 viewPager2) {
        this.context = context;
        this.seasonList = seasonList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeasonViewHolder(LayoutInflater.from(context).inflate(R.layout.episodes_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {

        holder.seasonName.setText(seasonList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder{

        private TextView seasonName;
        private RecyclerView episodeRecyler;

        public SeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            seasonName = itemView.findViewById(R.id.season_name_tv);
            episodeRecyler = itemView.findViewById(R.id.episodes_recycler);
        }
    }
}
