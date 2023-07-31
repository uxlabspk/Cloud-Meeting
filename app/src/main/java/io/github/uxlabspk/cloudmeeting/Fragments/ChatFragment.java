package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.uxlabspk.cloudmeeting.ChatBox;
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
        // prototype_chat_box
        binding.prototypeChatBox.setOnClickListener(view -> startActivity(new Intent(getContext(), ChatBox.class)));
    }
}