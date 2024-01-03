package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AssesmentDetailsAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentDetailsModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityAssesmentsAddAndRemoveBinding;

public class AssesmentsAddAndRemove extends AppCompatActivity {

    ActivityAssesmentsAddAndRemoveBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssesmentsAddAndRemoveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        // getting intent data
        className = getIntent().getStringExtra("className");

        // setting title
        binding.lectureTitle.setText(className);

        // goback
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // determining the user role.
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String userRole = pref.getString("User_role", null);

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            binding.addAssesments.setVisibility(View.VISIBLE);
        } else {
            binding.addAssesments.setVisibility(View.GONE);
        }

        // add new assesments
        binding.addAssesments.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddAssesments.class);
            intent.putExtra("ClassName", className);
            editor.putString("ClassName", className);
            editor.apply();
            startActivity(intent);
        });


        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // read the assesments in class
        ArrayList<AssesmentDetailsModel> allAssesments = new ArrayList<>();
        AssesmentDetailsAdapter adapter = new AssesmentDetailsAdapter();
        adapter.setAllAssesments(allAssesments, AssesmentsAddAndRemove.this);
        binding.rvAssesment.setLayoutManager(new LinearLayoutManager(AssesmentsAddAndRemove.this));

        editor.putString("ClassName", className);
        editor.apply();
        // Assesments
        mDatabase.getReference().child("Assesments").child(className).child("creation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.notFound.setVisibility(View.GONE);
                    if (!allAssesments.isEmpty()) allAssesments.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AssesmentDetailsModel model = dataSnapshot.getValue(AssesmentDetailsModel.class);
                        allAssesments.add(model);
                    }

                    binding.rvAssesment.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    binding.notFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}