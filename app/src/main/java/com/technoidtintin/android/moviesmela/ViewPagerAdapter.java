package com.technoidtintin.android.moviesmela;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.technoidtintin.android.moviesmela.Model.Season;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Season> episodeList;
    private int tvId, seasonNo;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<Season>episodeList,int tvId,int seasonNo) {
        super(fragmentActivity);
        this.episodeList = episodeList;
        this.tvId = tvId;
        this.seasonNo = seasonNo;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return EpisodeFragment.newInstance(position,episodeList,tvId,seasonNo);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }
}
