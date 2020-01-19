package com.technoidtintin.android.moviesmela.ui.TvSeasonDetails;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.Constant;
import com.technoidtintin.android.moviesmela.Episode;
import com.technoidtintin.android.moviesmela.Model.Season;
import com.technoidtintin.android.moviesmela.R;
import com.technoidtintin.android.moviesmela.SeasonDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EpisodeFragment extends Fragment {

    private static final String TAG = EpisodeFragment.class.getSimpleName();
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Season> seasons;
    private int tv_id, season_no;
    private SeasonDetailsViewModel seasonDetailsViewModel;
    private String apiKey;
    private MyEpisodeRecyclerViewAdapter episodeAdapter;
    private List<Episode> episodeList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EpisodeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EpisodeFragment newInstance(ArrayList<Season> seasonArrayList, int tvId, int seasonNo) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(Constant.SEASON_LIST, seasonArrayList);
        args.putInt(Constant.TV_ID, tvId);
        args.putInt(Constant.SEASON_NO, seasonNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            seasons = getArguments().getParcelableArrayList(Constant.SEASON_LIST);
            tv_id = getArguments().getInt(Constant.TV_ID);
            season_no = getArguments().getInt(Constant.SEASON_NO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episode_list, container, false);

        //Initialize ViewModel class
        seasonDetailsViewModel = ViewModelProviders.of(this).get(SeasonDetailsViewModel.class);

        //Initialize api key
        apiKey = getResources().getString(R.string.api_key);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            episodeAdapter = new MyEpisodeRecyclerViewAdapter(getContext(), mListener);
            recyclerView.setAdapter(episodeAdapter);
            getEpisodes(season_no);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Get Episodes
    private void getEpisodes(int season) {
        seasonDetailsViewModel.getSeasonDetails(tv_id, season, apiKey).observe(this, new Observer<SeasonDetails>() {
            @Override
            public void onChanged(SeasonDetails seasonDetails) {

                if (seasonDetails != null) {
                    if (episodeList.size() < 1) {
                        episodeList = seasonDetails.getEpisodes();
                        Log.e(TAG, "Season details is full");
                        if (episodeList != null) {
                            Log.e(TAG, "Episode details : " + episodeList.get(0).getStillPath());
                            episodeAdapter.setEpisodeList(episodeList);
                        } else {
                            Log.e(TAG, "Episode list is null");
                        }
                    } else {
                        episodeList.clear();
                        episodeList = seasonDetails.getEpisodes();
                        Log.e(TAG, "Season details is full");
                        if (episodeList != null) {
                            Log.e(TAG, "Episode details : " + episodeList.get(0).getStillPath());
                            episodeAdapter.setEpisodeList(episodeList);
                        } else {
                            Log.e(TAG, "Episode list is null");
                        }
                    }
                } else {
                    Log.e(TAG, "Episodes not available");
                    Toast.makeText(getContext(), "Episodes not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Episode item);
    }
}
