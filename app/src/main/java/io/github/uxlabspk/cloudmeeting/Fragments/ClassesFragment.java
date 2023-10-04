package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AllClassesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.JoinClass;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentClassesBinding;

public class ClassesFragment extends Fragment {

    FragmentClassesBinding binding;
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
        // Refresh the classes
        binding.refreshClasses.setOnClickListener(view -> refreshClasses());

        // all classes
        ArrayList<AllClassesModel> allClasses = new ArrayList<>();

        allClasses.add(new AllClassesModel("Mathematics", "Mon, Tue, Wed, Thr, Fri, Sat", "12:00AM"));
        allClasses.add(new AllClassesModel("Physics", "Mon, Tue, Wed, Thr, Fri, Sat", "01:00PM"));

        AllClassesAdapter adapter = new AllClassesAdapter();
        adapter.setAllClasses(allClasses, getContext());

        binding.rvClasses.setAdapter(adapter);
        binding.rvClasses.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void refreshClasses() {
        ProgressStatus ps = new ProgressStatus(getContext());
        ps.setTitle("Refreshing...");
        ps.show();
    }
}