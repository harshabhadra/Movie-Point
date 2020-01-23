package com.technoidtintin.android.moviesmela;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.technoidtintin.android.moviesmela.ui.Favorite.FavoriteActivity;
import com.technoidtintin.android.moviesmela.ui.Movies.MovieFragment;
import com.technoidtintin.android.moviesmela.ui.Tvshows.TvShowsFragment;
import com.technoidtintin.android.moviesmela.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //Remote config values
    private static final String VERSION_NAME_KEY = "version_name";
    private static final String APPLY_FORCE_UPDATE_KEY = "apply_force_update";
    private static final String SHOW_UPDATE_DIALOG_KEY = "show_update_dialog";
    private static final String UPDATE_MESSAGE = "update_message";

    HomeFragment homeFragment = new HomeFragment();
    MovieFragment movieFragment = new MovieFragment();
    TvShowsFragment tvShowsFragment = new TvShowsFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = homeFragment;

    private boolean showUpdateDialog;
    private String MY_APP_URL;
    private String version;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.navigation_movies:
                    fragmentManager.beginTransaction().hide(active).show(movieFragment).commit();
                    active = movieFragment;
                    return true;
                case R.id.navigation_tv_shows:
                    fragmentManager.beginTransaction().hide(active).show(tvShowsFragment).commit();
                    active = tvShowsFragment;
                    return true;
                case R.id.navigation_favorites:
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        MY_APP_URL  = getResources().getString(R.string.My_app_url);

        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, tvShowsFragment, "3").hide(tvShowsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, movieFragment, "2").hide(movieFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "1").commit();

        //Initializing Fab button
        FloatingActionButton refreshButton = findViewById(R.id.refresh_fab);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        //Get the Firebase Remote config instance
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        //Create a FireBase remote config setting
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .setMinimumFetchIntervalInSeconds(60)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        //Set Default values to FireBase Remote config
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        //Get The Version name
        version = getVersionName(this);

        //Get show update dialog value from shared preference
        showUpdateDialog = false;

    }

    @Override
    protected void onResume() {
        super.onResume();

            fetchValues();
    }

    //Get the version name
    private String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    //Fetch values from FireBase
    private void fetchValues() {

        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {

                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.e(TAG, "Remote Config values updated: " + updated);
                        } else {
                            Log.e(TAG, "Remote config values updated: " + task.getException());
                        }
                        displayUpdateDialog();
                    }
                });
    }

    //Display Update Dialog according to Remote config values
    private void displayUpdateDialog() {

        String latest_version = firebaseRemoteConfig.getString(VERSION_NAME_KEY);
        String updateMessage = firebaseRemoteConfig.getString(UPDATE_MESSAGE);

        Log.e(TAG, "config value version name: " + latest_version + " app version name: " + version);

        if (!(latest_version.equals(version)) && firebaseRemoteConfig.getBoolean(SHOW_UPDATE_DIALOG_KEY)
                && !(firebaseRemoteConfig.getBoolean(APPLY_FORCE_UPDATE_KEY))) {

            showUpdateDialog = true;

        } else if ((latest_version.equals(version)) && firebaseRemoteConfig.getBoolean(SHOW_UPDATE_DIALOG_KEY)
                && firebaseRemoteConfig.getBoolean(APPLY_FORCE_UPDATE_KEY)) {

            showUpdateDialog = true;
        }else {
            showUpdateDialog = false;
        }

        if (showUpdateDialog){
            createUpdateDialog(updateMessage);
        }

    }

    //Create Update Layout
    private void createUpdateDialog(String message) {

        View layout = getLayoutInflater().inflate(R.layout.update_dilog, null);

        TextView updateTv = layout.findViewById(R.id.update_message_tv);
        MaterialButton updateNowButton = layout.findViewById(R.id.update_now);
        MaterialButton updateLaterButton = layout.findViewById(R.id.update_later);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        updateTv.setText(message);
        updateLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                showUpdateDialog = false;
            }
        });

        updateNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_APP_URL));
                startActivity(intent);
                showUpdateDialog = false;
            }
        });
    }
}
