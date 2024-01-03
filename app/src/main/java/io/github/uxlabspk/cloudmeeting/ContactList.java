package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.giphy.sdk.core.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.uxlabspk.cloudmeeting.Adapters.AllLecturesAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.ContactAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.AllMessagesModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
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
        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // progress bar
        ProgressStatus ps = new ProgressStatus(this);
        ps.setTitle("Searching Contacts...");
        ps.setCanceledOnTouchOutside(false);

        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        //
        ArrayList<Users> contactsList = new ArrayList<>();

        // search contacts
        binding.searchBtn.setOnClickListener(view -> {

                if (contactsList != null) contactsList.clear();

                if (mAuth.getCurrentUser().getEmail().matches(binding.searchInput.getText().toString())) {
                    binding.notFound.setText("Your Email, Try another one.");
                    binding.notFound.setVisibility(View.VISIBLE);
                } else {
                    ps.show();
                    mDatabase.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Users users = ds.child("Profile").getValue(Users.class);
                                    if (users.getUserEmail().matches(binding.searchInput.getText().toString())) {
                                        binding.notFound.setVisibility(View.GONE);
                                        contactsList.add(users);
                                        ps.dismiss();
                                        break;
                                    }
                                    else {
                                        ps.dismiss();
                                        binding.notFound.setText("No results found");
                                        binding.notFound.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                ps.dismiss();
                                binding.notFound.setText("No results found");
                                binding.notFound.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                ContactAdapter adapter = new ContactAdapter();
                adapter.setContacts(contactsList);
                binding.rvContacts.setAdapter(adapter);
                binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));

        });
    }
}