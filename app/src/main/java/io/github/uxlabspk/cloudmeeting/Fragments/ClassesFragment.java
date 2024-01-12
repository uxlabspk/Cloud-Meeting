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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AllClassesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.ContactList;
import io.github.uxlabspk.cloudmeeting.JoinClass;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.add_classes_activity;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentClassesBinding;

public class ClassesFragment extends Fragment {

    FragmentClassesBinding binding;
    private ArrayList<AllClassesModel> allClass;
    private AllClassesAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private String userSection;
    private String userRole;

    private String userSchool;
    public ClassesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // on add class
        binding.addClasses.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), add_classes_activity.class));
        });

        // getting list of users.
        allClass = new ArrayList<>();
        adapter = new AllClassesAdapter();
        adapter.setAllClasses(allClass, getContext());
        binding.rvClasses.setLayoutManager(new LinearLayoutManager(getContext()));
        getClasses();

        // determining the user role.
        SharedPreferences pref = getActivity().getSharedPreferences("User_role", Context.MODE_PRIVATE);
        userRole = pref.getString("User_role", null);
        userSection = pref.getString("User_class", null);
        userSchool = pref.getString("User_School", null);

        // Toast.makeText(getContext(), userRole, Toast.LENGTH_SHORT).show();

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            binding.addClasses.setVisibility(View.VISIBLE);

        } else {
            binding.addClasses.setVisibility(View.GONE);
        }

        // add new class module
        binding.addClasses.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), add_classes_activity.class));
        });
    }

    private void getClasses() {
        mDatabase.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.notFound.setVisibility(View.GONE);
                    if (!allClass.isEmpty()) allClass.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AllClassesModel classesModel = dataSnapshot.getValue(AllClassesModel.class);
                        if (userRole.matches("Teacher")) {
                            if (classesModel.getTeacher_id().matches(mAuth.getCurrentUser().getEmail())) {
                                allClass.add(classesModel);
                            }
                        } else {
                            if (classesModel.getSectionName().matches(userSection) && classesModel.getSchoolName().matches(userSchool)) {
                                allClass.add(classesModel);
                            }
                        }
                    }
                    binding.rvClasses.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    binding.notFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}