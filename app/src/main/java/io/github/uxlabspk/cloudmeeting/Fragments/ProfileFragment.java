package io.github.uxlabspk.cloudmeeting.Fragments;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.uxlabspk.cloudmeeting.Attendance;
import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.DrawingActivity;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.ProfileEdit;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.SplashScreen;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // setting user detials.
        mDatabase.getRef().child(mAuth.getCurrentUser().getUid()).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllChatUsersModel user = snapshot.getValue(AllChatUsersModel.class);
                String name = user.getUserName();
                String email = user.getUserEmail();

                binding.userName.setText(name);
                binding.userEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                startActivity(new Intent(getContext(), SplashScreen.class));
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
        startActivity(new Intent(getContext(), SplashScreen.class));
    }
}