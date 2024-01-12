package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllAssesmentAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AllLecturesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.ViewAssesments;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentQuizBinding;

public class QuizFragment extends Fragment {
    FragmentQuizBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    private String userSection;
    private String userSchool;
    private String userRole;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // determining the user role.
        SharedPreferences pref = getActivity().getSharedPreferences("User_role", Context.MODE_PRIVATE);
        userSection = pref.getString("User_class", null);
        userSchool = pref.getString("User_School", null);
        userRole = pref.getString("User_role", null);

        ArrayList<AllAssesmentsModel> allAssesments = new ArrayList<>();

        mDatabase.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) binding.notFound.setVisibility(View.VISIBLE);
                if (!allAssesments.isEmpty()) allAssesments.clear();
                else {
                    binding.notFound.setVisibility(View.GONE);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        AllClassesModel classesModel = ds.getValue(AllClassesModel.class);
                        if (userRole.matches("Teacher")) {
                            if (classesModel.getTeacher_id().matches(mAuth.getCurrentUser().getEmail())) {
                                AllAssesmentsModel m = new AllAssesmentsModel(classesModel.getClass_name(), classesModel.getAssesments_url());
                                allAssesments.add(m);
                            }
                        } else {
                            if (userSection.matches(classesModel.getSectionName()) && userSchool.matches(classesModel.getSchoolName())) {
                                AllAssesmentsModel m = new AllAssesmentsModel(classesModel.getClass_name(), classesModel.getAssesments_url());
                                allAssesments.add(m);
                            }
                        }
                    }
                }

                AllAssesmentAdapter adapter = new AllAssesmentAdapter();
                adapter.setAllAssesments(allAssesments, getContext());
                binding.rvAllAssesments.setAdapter(adapter);
                binding.rvAllAssesments.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}