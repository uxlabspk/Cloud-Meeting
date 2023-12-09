package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.github.uxlabspk.cloudmeeting.databinding.ActivityContactListBinding;

public class ContactList extends AppCompatActivity {

    private ActivityContactListBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        init();
        setContentView(binding.getRoot());
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        StringBuilder sb = new StringBuilder();

        mDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
//                    ds.getKey()
                    // sb.append(ds.toString());

//                    Toast.makeText(ContactList.this, sb.toString(), Toast.LENGTH_SHORT).show();
//                    binding.text.setText(sb.toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}