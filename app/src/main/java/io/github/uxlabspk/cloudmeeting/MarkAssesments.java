package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityMarkAssesmentsBinding;

public class MarkAssesments extends AppCompatActivity {

    private ActivityMarkAssesmentsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMarkAssesmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // on back press
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // getting the user details
        binding.studentID.setText(getIntent().getStringExtra("StudentID"));

        // mark the assignment
        binding.markAssessment.setOnClickListener(view -> {
            mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("className")).child("submission").child(getIntent().getStringExtra("assessmentTitle").replaceAll(" ", "")).child(getIntent().getStringExtra("uid")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    SubmissionModel model = snapshot.getValue(SubmissionModel.class);
                    if (binding.studentMarks.getText() == null) binding.assesmentMarksLayout.setError("Please fill this field");
                    model.setStudentMarks(binding.studentMarks.getText().toString());
                    model.setStudentRemarks(binding.studentRemarks.getText().toString());

                    mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("className")).child("submission").child(getIntent().getStringExtra("assessmentTitle").replaceAll(" ", "")).child(getIntent().getStringExtra("uid")).setValue(model);

                    Toast.makeText(MarkAssesments.this, "Successfully Marked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        });
    }
}