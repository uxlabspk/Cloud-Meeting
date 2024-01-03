package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.ContactList;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentChatBinding;


public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ProgressStatus ps;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ArrayList<AllChatUsersModel> allChatUsers;
    private AllChatUsersAdapter adapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // on chat refresh

        // on add contacts
        binding.addChat.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ContactList.class));
        });

        // getting list of users.
        allChatUsers = new ArrayList<>();
        adapter = new AllChatUsersAdapter();
        adapter.setAllChatUsers(allChatUsers, getContext());
        binding.rvAllChats.setAdapter(adapter);
        binding.rvAllChats.setLayoutManager(new LinearLayoutManager(getContext()));
        getUsers();
    }

    private void getUsers() {
        mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.notFound.setVisibility(View.GONE);

                if (snapshot.exists()) {
                    if (allChatUsers != null) allChatUsers.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        AllChatUsersModel model = ds.getValue(AllChatUsersModel.class);
                        allChatUsers.add(model);
                    }


                    binding.rvAllChats.setAdapter(adapter);
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