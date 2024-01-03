package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        ArrayList<AssesmentResultModel> allSubmissions = new ArrayList<>();
        AssesmentResultAdapter adapter = new AssesmentResultAdapter();
        adapter.setAllAssesmentResult(allSubmissions);
        binding.rvSubmissions.setAdapter(adapter);
        binding.rvSubmissions.setLayoutManager(new LinearLayoutManager(ViewAssesmentSubmissions.this));
        mDatabase.getReference().child("Assesments").child(pref.getString("ClassName", null)).child("Submission").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    binding.notFound.setVisibility(View.VISIBLE);
                } else {
                    binding.notFound.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        AssesmentResultModel model = dataSnapshot.getValue(AssesmentResultModel.class);
                        allSubmissions.add(model);
                    }
                    binding.rvSubmissions.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}