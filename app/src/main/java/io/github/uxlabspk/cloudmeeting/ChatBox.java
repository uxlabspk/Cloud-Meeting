package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import io.github.uxlabspk.cloudmeeting.Adapters.MessageAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AllMessagesModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityChatBoxBinding;

public class ChatBox extends AppCompatActivity {

    ActivityChatBoxBinding binding;
    private ArrayList<AllMessagesModel> allMessages = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // getting data from intent
        String userName = getIntent().getStringExtra("userName");
        String availability = getIntent().getStringExtra("availability");
        binding.userName.setText(userName);
        binding.userActive.setText(availability);

        // setting the recycler view
        adapter = new MessageAdapter();
        adapter.setAllMessages(allMessages, this);

        binding.rvUserChat.setAdapter(adapter);
        // linear layout manager here.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        binding.rvUserChat.setLayoutManager(llm);

        // on message sent.
        binding.sendBtn.setOnClickListener(view -> {
            String message = binding.messageText.getText().toString().trim();
            sentMessage(message, AllMessagesModel.SENT_BY_ME, new Date().getTime());
            binding.messageText.setText("");
        });
    }

    private void sentMessage(String message, String sentBy, long time) {
        runOnUiThread(() -> {
            AllMessagesModel messages = new AllMessagesModel(message, sentBy, time);
            messages.setSeen(true);
            allMessages.add(messages);

            // just for prototyping only
            allMessages.add(new AllMessagesModel(message, AllMessagesModel.SENT_BY_YOU, time));

            adapter.notifyDataSetChanged();
            binding.rvUserChat.smoothScrollToPosition(adapter.getItemCount());
        });
    }
}