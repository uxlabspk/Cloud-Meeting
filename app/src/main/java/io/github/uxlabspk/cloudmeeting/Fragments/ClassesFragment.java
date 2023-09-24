package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.uxlabspk.cloudmeeting.JoinClass;
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
//        prototype_class_box

        binding.prototypeClassBox.setOnClickListener(view -> startActivity(new Intent(getContext(), JoinClass.class)));

    }
}