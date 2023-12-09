package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.uxlabspk.cloudmeeting.Fragments.ChatFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ClassesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.LecturesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ProfileFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.QuizFragment;

public class business extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // Calling battery optimizer function.
        checkForBatteryOptimization();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView);

        // On UI refresh, No transaction will occur.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.edu_chat:
                    fragment = new ChatFragment();
                    break;
                case R.id.edu_classes:
                    fragment = new ClassesFragment();
                    break;
                case R.id.edu_profile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    return false;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        });
    }




    /*
     * Disable battery optimization
     */
    public void checkForBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }
}