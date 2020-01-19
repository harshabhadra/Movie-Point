package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.technoidtintin.android.moviesmela.Model.Season;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Season> seasonList;
    private int tvId;
    private int seasonNo;
    private boolean isContainZero;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Season> seasonList, int tvId,int seasonNo, boolean isZero) {
        super(fragmentActivity);
        this.seasonList = seasonList;
        this.tvId = tvId;
        this.seasonNo = seasonNo;
        isContainZero = isZero;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (isContainZero) {
            return EpisodeFragment.newInstance(seasonList, tvId, position);
        }else {
            return EpisodeFragment.newInstance(seasonList,tvId,position + 1);
        }
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }
}
