package io.github.uxlabspk.cloudmeeting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // light dark mode.
        SharedPreferences preferences = getSharedPreferences("THEME_MODE", MODE_PRIVATE);
        boolean isNightMode = preferences.getBoolean("NIGHT_MODE", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        new Handler().postDelayed(() -> {
            // user login details.
            SharedPreferences userDetails = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
            boolean isLogin = userDetails.getBoolean("IS_LOGIN", false);
            if (isLogin) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreen.this, MeetingType.class));
                finish();
            }
        }, 1500);
    }
}
