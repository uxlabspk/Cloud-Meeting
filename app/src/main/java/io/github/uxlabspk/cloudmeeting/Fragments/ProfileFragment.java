package io.github.uxlabspk.cloudmeeting.Fragments;
import android.content.Intent;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // custom fields.
    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false);
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
        // edu_attendace_report
        binding.eduAttendaceReport.setOnClickListener(view -> startActivity(new Intent(getContext(), Attendance.class)));
        // toggle_ui_mode
        binding.toggleUiMode.setOnClickListener(view -> toggleUIMode());
        // edu_user_logout
        binding.eduUserLogout.setOnClickListener(view -> logoutUser());

        // set dark mode toggler flase
        binding.toggleUiMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void toggleUIMode() {
        if (binding.toggleUiMode.isChecked()) {
            Toast.makeText(getContext(), "UI Dark Mode Enabled", Toast.LENGTH_LONG).show();
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            Toast.makeText(getContext(), "UI Light Mode Enabled", Toast.LENGTH_LONG).show();
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void logoutUser() {
        // logout the user from our database
        // Hardcoded Action
        clearAllData();
        startActivity(new Intent(getContext(), SplashScreen.class));
    }

    private void clearAllData() {
        // delete all data from device.
    }
}