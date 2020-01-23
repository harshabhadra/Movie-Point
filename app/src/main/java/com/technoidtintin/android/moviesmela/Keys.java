package com.technoidtintin.android.moviesmela;

public class Keys {

    static {
        System.loadLibrary("native-lib");
    }

    public native String getKey();
}
