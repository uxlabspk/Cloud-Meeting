package io.github.uxlabspk.cloudmeeting.Fragments;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.github.uxlabspk.cloudmeeting.Attendance;
import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.DrawingActivity;
import io.github.uxlabspk.cloudmeeting.EditProfile;
import io.github.uxlabspk.cloudmeeting.MeetingType;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.SplashScreen;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

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
        // ready the database
         mAuth = FirebaseAuth.getInstance();
         mDatabase = FirebaseDatabase.getInstance();

         // getting user data from database.
        mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userDetails = snapshot.getValue(Users.class);
                binding.userName.setText(userDetails.getUserName());    // setting user name
                binding.userEmail.setText(userDetails.getUserEmail());  // setting user email
                // setting user photo
                if (userDetails.getUserImgUrl().isEmpty()) binding.userProfilePic.setImageResource(R.drawable.ic_profile);
                else Picasso.get().load(userDetails.getUserImgUrl()).placeholder(R.drawable.ic_profile).into(binding.userProfilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // edit profile
        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(getContext(), EditProfile.class)));

        // edu_whiteboard
        binding.eduWhiteboard.setOnClickListener(view -> startActivity(new Intent(getContext(), DrawingActivity.class)));

        // determining the user role
        SharedPreferences pref = getContext().getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userRole = pref.getString("User_role", null);

        if (userRole.matches("Teacher")) {
            // quizzes hide
             binding.eduQuizzesResults.setVisibility(View.GONE);
        }

        if (userRole.matches("Parent")) {
            // quizzes, attendance hide
            binding.eduQuizzesResults.setVisibility(View.GONE);
            binding.eduAttendaceReport.setVisibility(View.GONE);
        }

        // edu_quizzes_results
        binding.eduQuizzesResults.setOnClickListener(view -> startActivity(new Intent(getContext(), QuizResults.class)));

        // edu_attendance_report
        binding.eduAttendaceReport.setOnClickListener(view -> startActivity(new Intent(getContext(), Attendance.class)));

        // toggle_ui_mode (Dark/Light Mode)
        binding.toggleUiMode.setOnClickListener(view -> toggleUIMode());

        // edu_user_logout
        binding.eduUserLogout.setOnClickListener(view -> logoutUser());

        // set dark mode toggle (on, off)
        binding.toggleUiMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);

        // delete account
        binding.eduUserDelete.setOnClickListener(view -> {
            ConfirmDialog cd = new ConfirmDialog(getContext(), Type.CONFIRM);
            cd.setCanceledOnTouchOutside(false);
            cd.setDialog_headline("Confirm to Delete");
            cd.setDialog_body("Are you sure to delete your account and all data?");
            cd.setYes_btn_text("Delete");
            cd.setNo_btn_text("Cancel");

            cd.getYes_btn().setOnClickListener(view1 -> {
                mAuth.getCurrentUser().delete();
                mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).removeValue();
                startActivity(new Intent(getContext(), MeetingType.class));
                cd.dismiss();
            });

            cd.getNo_btn().setOnClickListener(view2 -> {
                cd.dismiss();
            });

            cd.show();
        });
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
        mAuth.signOut();
        startActivity(new Intent(getContext(), MeetingType.class));
        getActivity().finish();
    }
}