package io.github.uxlabspk.cloudmeeting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setAppTheme();

        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() != null) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreen.this, MeetingType.class));
                finish();
            }
        }, 1500);
    }

    private void setAppTheme() {
        // toggle app theme.
        SharedPreferences preferences = getSharedPreferences("THEME_MODE", MODE_PRIVATE);
        boolean isNightMode = preferences.getBoolean("NIGHT_MODE", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
