package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllAssesmentAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.ViewAssesments;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentQuizBinding;

public class QuizFragment extends Fragment {
    FragmentQuizBinding binding;

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
        // refreshAssesments
        binding.refreshAssesments.setOnClickListener(view -> refreshAssesments());

        ArrayList<AllAssesmentsModel> allAssesments = new ArrayList<>();

        allAssesments.add(new AllAssesmentsModel("Assignment 1", "June 21 - June 28", 120445002L));
        allAssesments.add(new AllAssesmentsModel("Assignment 2", "June 19 - June 20", 120445442L));
        allAssesments.add(new AllAssesmentsModel("Assignment 3", "June 12 - June 14", 120448302L));
        allAssesments.add(new AllAssesmentsModel("Quiz 1", "June 18 - June 22", 121235002L));
        allAssesments.add(new AllAssesmentsModel("Assignment 1", "June 01 - June 10", 402445002L));

        AllAssesmentAdapter adapter = new AllAssesmentAdapter();
        binding.rvAllAssesments.setAdapter(adapter);
        adapter.setAllAssesments(allAssesments, getContext());
        binding.rvAllAssesments.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void refreshAssesments() {
        ProgressStatus ps = new ProgressStatus(getContext());
        ps.setTitle("Refreshing...");
        ps.show();
    }
}