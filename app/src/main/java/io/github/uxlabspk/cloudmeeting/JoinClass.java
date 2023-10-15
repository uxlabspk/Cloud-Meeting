package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.uxlabspk.cloudmeeting.databinding.ActivityDrawingBinding;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityJoinClassBinding;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentLecturesBinding;

public class JoinClass extends AppCompatActivity {

    ActivityJoinClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());
    }
}