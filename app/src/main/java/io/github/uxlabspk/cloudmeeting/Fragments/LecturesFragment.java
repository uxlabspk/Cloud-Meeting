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

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllLecturesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.LecturesDetails;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentLecturesBinding;

public class LecturesFragment extends Fragment {
    FragmentLecturesBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String userSection;
    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLecturesBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // Ready the Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // determining the user role.
        SharedPreferences pref = getActivity().getSharedPreferences("User_role", Context.MODE_PRIVATE);
        userSection = pref.getString("User_class", null);

        ArrayList<AllLecturesModel> allLectures = new ArrayList<>();

        mDatabase.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AllClassesModel classesModel = ds.getValue(AllClassesModel.class);
                    if (userSection.matches(classesModel.getSectionName())) {
                        AllLecturesModel m = new AllLecturesModel(classesModel.getClass_name(), classesModel.getLectures_url());
                        allLectures.add(m);
                    }
                }

                AllLecturesAdapter adapter = new AllLecturesAdapter();
                adapter.setAllLectures(allLectures, getContext());
                binding.rvLectures.setAdapter(adapter);
                binding.rvLectures.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}