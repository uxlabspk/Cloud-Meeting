package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityAssesmentDetailsBinding;

public class AssesmentDetails extends AppCompatActivity {

    private ActivityAssesmentDetailsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssesmentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // getting intent data
        String Title = getIntent().getStringExtra("Title");
        String Class = getIntent().getStringExtra("Class");
        String TotalMarks = getIntent().getStringExtra("TotalMarks");

        // getting the data from database
        mDatabase.getReference().child("Assesments").child(Class).child("submission").child(Title.replaceAll(" ", "")).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SubmissionModel model = snapshot.getValue(SubmissionModel.class);
                    binding.assessmentTitle.setText(Title);
                    binding.obtainMarks.setText(model.getStudentMarks() + "/" + TotalMarks);
                    binding.assessmentRemarks.setText(model.getStudentRemarks());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}