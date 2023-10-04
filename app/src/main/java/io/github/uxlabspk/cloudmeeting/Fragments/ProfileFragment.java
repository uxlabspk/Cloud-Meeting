package io.github.uxlabspk.cloudmeeting.Fragments;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.github.uxlabspk.cloudmeeting.Attendance;
import io.github.uxlabspk.cloudmeeting.DrawingActivity;
import io.github.uxlabspk.cloudmeeting.ProfileEdit;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.SplashScreen;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // edu_edit_profile
        binding.eduEditProfile.setOnClickListener(view -> startActivity(new Intent(getContext(), ProfileEdit.class)));
        // edu_whiteboard
        binding.eduWhiteboard.setOnClickListener(view -> startActivity(new Intent(getContext(), DrawingActivity.class)));
        // edu_quizzes_results
        binding.eduQuizzesResults.setOnClickListener(view -> startActivity(new Intent(getContext(), QuizResults.class)));
        // edu_attendance_report
        binding.eduAttendaceReport.setOnClickListener(view -> startActivity(new Intent(getContext(), Attendance.class)));
        // toggle_ui_mode
        binding.toggleUiMode.setOnClickListener(view -> toggleUIMode());
        // edu_user_logout
        binding.eduUserLogout.setOnClickListener(view -> logoutUser());
        // set dark mode toggle
        binding.toggleUiMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void toggleUIMode() {
        SharedPreferences pref = getActivity().getSharedPreferences("THEME_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (binding.toggleUiMode.isChecked()) {
            editor.putBoolean("NIGHT_MODE", true);
            editor.apply();
            binding.toggleUiMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            editor.putBoolean("NIGHT_MODE", false);
            editor.apply();
            binding.toggleUiMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void logoutUser() {
        // logout the user from our database Hardcoded Action.
        clearAllData();
        startActivity(new Intent(getContext(), SplashScreen.class));
    }

    private void clearAllData() {
        // Clearing user login state from the device.
        SharedPreferences userDetails = getActivity().getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putBoolean("IS_LOGIN", false);
        editor.apply();
    }
}