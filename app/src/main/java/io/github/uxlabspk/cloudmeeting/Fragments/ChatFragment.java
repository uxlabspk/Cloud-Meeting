package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.ChatBox;
import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.ContactList;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentChatBinding;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentProfileBinding;


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
        binding.refreshChat.setOnClickListener(view -> refreshChat());

        // on add contacts
        binding.addChat.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ContactList.class));
        });

        // getting list of users.
        ArrayList<AllChatUsersModel> allChatUsers = new ArrayList<>();
        AllChatUsersAdapter adapter = new AllChatUsersAdapter();
        adapter.setAllChatUsers(allChatUsers, getContext());
        binding.rvAllChats.setLayoutManager(new LinearLayoutManager(getContext()));
        getUsers();
    }

    private void refreshChat() {
        ps = new ProgressStatus(getContext());
        ps.setTitle("Refreshing...");
        ps.show();

        getUsers();
    }

    private void getUsers() {
//        ps.dismiss();
        mDatabase.getReference().child(mAuth.getCurrentUser().getUid()).child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                allChatUsers.clear();

                if (snapshot.exists()) {
                    binding.notFound.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        // AllChatUsersModel model = dataSnapshot.getValue(AllChatUsersModel.class);
                        // allChatUsers.add(model);
                        String data = dataSnapshot.getValue(String.class);
//                        allChatUsers.add(data);
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