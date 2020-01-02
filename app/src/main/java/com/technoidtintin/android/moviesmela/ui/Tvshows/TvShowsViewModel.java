package com.technoidtintin.android.moviesmela.ui.Tvshows;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TvShowsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}