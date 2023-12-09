package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllLecturesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.LecturesDetails;
import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentLecturesBinding;

public class LecturesFragment extends Fragment {
    FragmentLecturesBinding binding;
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

        // all lectures
        ArrayList<AllLecturesModel> allLectures = new ArrayList<>();

        allLectures.add(new AllLecturesModel("Physics", 23));
        allLectures.add(new AllLecturesModel("Programming", 23));
        allLectures.add(new AllLecturesModel("Chemistry", 23));
        allLectures.add(new AllLecturesModel("Mathematics", 23));
        allLectures.add(new AllLecturesModel("English", 23));
        allLectures.add(new AllLecturesModel("Urdu", 23));

        AllLecturesAdapter adapter = new AllLecturesAdapter();
        adapter.setAllLectures(allLectures, getContext());
        binding.rvLectures.setAdapter(adapter);
        binding.rvLectures.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void refreshLectures() {
        ProgressStatus ps = new ProgressStatus(getContext());
        ps.setTitle("Refreshing...");
        ps.show();
    }
}