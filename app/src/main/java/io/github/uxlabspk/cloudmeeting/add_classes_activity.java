package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityAddClassesBinding;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityLoginBinding;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentClassesBinding;

public class add_classes_activity extends AppCompatActivity {

    private ActivityAddClassesBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddClassesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        // go back
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // Ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // read local data
        SharedPreferences pref = getSharedPreferences("User_role", MODE_PRIVATE);
        String userSchool = pref.getString("User_School", null);
        String userRole = pref.getString("User_role", null);

        // checking the user role.
        if (userRole.matches("Teacher")) {
            binding.className.setHint("Enter Class Name");
            binding.classTime.setHint("Enter Class Time");
            binding.sectionName.setHint("Enter Section Name");
        } else if (userRole.matches("Admin")) {
            binding.className.setHint("Enter Meeting Name");
            binding.classTime.setHint("Enter Meeting Time");
            binding.sectionName.setHint("Enter Department Name");
        }

        // adding the class
        binding.createClass.setOnClickListener(view -> {
            String lectures_url = binding.className.getText().toString().toLowerCase().trim() + "/lectures/" + mAuth.getCurrentUser().getUid() + "/";
            String assesments_url = binding.className.getText().toString().toLowerCase().trim() + "/assesments/" + mAuth.getCurrentUser().getUid() + "/";
            AllClassesModel classesModel = new AllClassesModel(userSchool, binding.sectionName.getText().toString(), binding.className.getText().toString().trim(), mAuth.getCurrentUser().getEmail(), binding.classTime.getText().toString().trim(), lectures_url, assesments_url);

            ProgressStatus ps = new ProgressStatus(add_classes_activity.this);
            ps.setTitle("Creating Class");
            ps.setCanceledOnTouchOutside(false);
            ps.show();

            mDatabase.getReference().child("Class").child(binding.className.getText().toString().replace(" ", "")).setValue(classesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ps.dismiss();
                        Toast.makeText(add_classes_activity.this, "Successfully created your class", Toast.LENGTH_SHORT).show();
                        // clear the form
                        binding.className.setText(null);
                        binding.classTime.setText(null);
                        binding.sectionName.setText(null);
                    } else {
                        ps.dismiss();
                        Toast.makeText(add_classes_activity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}