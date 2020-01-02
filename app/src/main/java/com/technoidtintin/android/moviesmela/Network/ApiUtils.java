package com.technoidtintin.android.moviesmela.Network;

import com.technoidtintin.android.moviesmela.Network.ApiServices;
import com.technoidtintin.android.moviesmela.Network.RetrofitClient;

public class ApiUtils {

    public ApiUtils() {
    }

    public static final String BASE_URL = "http://api.themoviedb.org/";

    public static ApiServices getApiServices(){
        return RetrofitClient.getRetrofit(BASE_URL).create(ApiServices.class);
    }
    public static Operator getOperator(){
        return RetroFitGsonClient.getRetrofit(BASE_URL).create(Operator.class);
    }
}
