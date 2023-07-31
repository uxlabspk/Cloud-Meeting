package io.github.uxlabspk.cloudmeeting;

import io.github.uxlabspk.cloudmeeting.Fragments.ChatFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ClassesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.LecturesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ProfileFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.QuizFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        checkForBatteryOptimization();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commitNow();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.edu_chat:
                    fragment = new ChatFragment();
                    break;
                case R.id.edu_classes:
                    fragment = new ClassesFragment();
                    break;
                case R.id.edu_lectures:
                    fragment = new LecturesFragment();
                    break;
                case R.id.edu_quizzes:
                    fragment = new QuizFragment();
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




    /**
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