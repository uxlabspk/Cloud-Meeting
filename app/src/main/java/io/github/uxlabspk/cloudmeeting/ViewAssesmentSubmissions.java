package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AssesmentResultAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentResultModel;
import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityViewAssesmentSubmissionsBinding;

public class ViewAssesmentSubmissions extends AppCompatActivity {
    ActivityViewAssesmentSubmissionsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAssesmentSubmissionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goback
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // getting the data from user.
        getIntent().getStringExtra("className");
        getIntent().getStringExtra("AssesmentTitle");
        getIntent().getStringExtra("AssesmentDetails");
        getIntent().getStringExtra("AssesmentDeadline");
        getIntent().getStringExtra("AssesmentMarks");
        getIntent().getStringExtra("AssessmentUrl");

        ArrayList<SubmissionModel> allSubmissions = new ArrayList<>();
        AssesmentResultAdapter adapter = new AssesmentResultAdapter();
        adapter.setAllAssesmentResult(allSubmissions, this, getIntent().getStringExtra("className"), getIntent().getStringExtra("AssesmentTitle"));
        binding.rvSubmissions.setAdapter(adapter);
        binding.rvSubmissions.setLayoutManager(new LinearLayoutManager(ViewAssesmentSubmissions.this));
        mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("className")).child("submission").child(getIntent().getStringExtra("AssesmentTitle").replaceAll(" ", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    binding.notFound.setVisibility(View.VISIBLE);
                } else {
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        if (!allSubmissions.isEmpty()) allSubmissions.clear();
                        binding.notFound.setVisibility(View.GONE);
                        SubmissionModel model = ds.getValue(SubmissionModel.class);
                        allSubmissions.add(model);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}