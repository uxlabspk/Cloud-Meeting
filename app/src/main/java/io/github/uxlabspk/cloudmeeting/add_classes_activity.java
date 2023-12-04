package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
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
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        // Ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // adding the class
        binding.createClass.setOnClickListener(view -> {
            HashMap<String, String> classData = new HashMap<>();
            classData.put("Name", binding.className.getText().toString().trim());
            classData.put("Time", binding.classTime.getText().toString().trim());

            ProgressStatus ps = new ProgressStatus(add_classes_activity.this);
            ps.setTitle("Creating Class");
            ps.setCanceledOnTouchOutside(false);
            ps.show();

            mDatabase.getReference().child(mAuth.getCurrentUser().getUid()).child("Class").child(binding.className.getText().toString().replace(" ", "")).setValue(classData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ps.dismiss();
                        Toast.makeText(add_classes_activity.this, "Successfully created your class", Toast.LENGTH_SHORT).show();
                        // clear the form
                        binding.className.setText(null);
                        binding.classTime.setText(null);
                    } else {
                        ps.dismiss();
                        Toast.makeText(add_classes_activity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}