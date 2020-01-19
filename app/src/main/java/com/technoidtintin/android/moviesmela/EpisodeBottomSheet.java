package com.technoidtintin.android.moviesmela;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.technoidtintin.android.moviesmela.databinding.FragmentEpisodeBottomSheetBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeBottomSheet extends BottomSheetDialogFragment {

    private Episode episode;
    public EpisodeBottomSheet(Episode episode) {
        // Required empty public constructor
        this.episode = episode;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEpisodeBottomSheetBinding episodeBottomSheetBinding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_episode_bottom_sheet,container,false);

        episodeBottomSheetBinding.setEpisode(episode);

        //Close bottom Sheet
        episodeBottomSheetBinding.episodeBottomCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return episodeBottomSheetBinding.getRoot();
    }

}
