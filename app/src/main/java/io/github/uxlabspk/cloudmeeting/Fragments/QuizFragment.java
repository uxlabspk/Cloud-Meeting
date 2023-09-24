package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // view Results
        binding.viewResults.setOnClickListener(view -> startActivity(new Intent(getContext(), QuizResults.class)));

        // View details
        binding.prototypeAssesmentBox.setOnClickListener(view -> startActivity(new Intent(getContext(), ViewAssesments.class)));
    }
}