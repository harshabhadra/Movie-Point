package com.technoidtintin.android.moviesmela.ui.Favorite;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FavViewPagerAdapter extends FragmentStateAdapter {

    public FavViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new FavoriteFragment().newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
