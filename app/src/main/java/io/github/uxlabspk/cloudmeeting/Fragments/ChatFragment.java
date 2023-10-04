package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.ChatBox;
import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentChatBinding;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentProfileBinding;


public class ChatFragment extends Fragment {

    FragmentChatBinding binding;

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
        // Refresh the chat
        binding.refreshChat.setOnClickListener(view -> refreshChat());

        // all chat users
        ArrayList<AllChatUsersModel> allChatUsers = new ArrayList<>();

        allChatUsers.add(new AllChatUsersModel("Muhammad Naveed", "hi, everyone", 120000002L));
        allChatUsers.add(new AllChatUsersModel("Muhammad Usama", "hello", 120445002L));
        allChatUsers.add(new AllChatUsersModel("Talat Mehmood", "hi, everyone", 250451242L));
        allChatUsers.add(new AllChatUsersModel("Humayoun Asim", "hi, everyone", 555509992L));

        AllChatUsersAdapter adapter = new AllChatUsersAdapter();
        adapter.setAllChatUsers(allChatUsers, getContext());

        binding.rvAllChats.setAdapter(adapter);
        binding.rvAllChats.setLayoutManager(new LinearLayoutManager(getContext()));



        // show no records found status
        if (allChatUsers.size() == 0)
            binding.notFound.setVisibility(View.VISIBLE);
        else
            binding.notFound.setVisibility(View.GONE);
    }

    private void refreshChat() {
        ProgressStatus ps = new ProgressStatus(getContext());
        ps.setTitle("Refreshing...");
        ps.show();
    }
}