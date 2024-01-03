package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import io.github.uxlabspk.cloudmeeting.Adapters.MessageAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AllMessagesModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityChatBoxBinding;

public class ChatBox extends AppCompatActivity {

    private ActivityChatBoxBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ArrayList<AllMessagesModel> allMessages = new ArrayList<>();
    private MessageAdapter adapter;

    private String receiverUID;
    private String senderUID;
    private String senderRoom;
    private String receiverRoom;

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

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // getting data from intent
        String userName = getIntent().getStringExtra("userName");
        String profileImg = getIntent().getStringExtra("profileImg");
        String uid = getIntent().getStringExtra("UID");

        // setting up the chat room
        receiverUID = uid;
        senderUID = mAuth.getCurrentUser().getUid();
        senderRoom = senderUID + receiverUID;
        receiverRoom = receiverUID + senderUID;

        // setting the name and profile img
        binding.userName.setText(userName);
        if (profileImg.isEmpty()) binding.userProfile.setImageResource(R.drawable.ic_profile);
        else Picasso.get().load(profileImg).placeholder(R.drawable.ic_profile).into(binding.userProfile);

        // getting messages
        getMessages();

        // setting the recycler view
        adapter = new MessageAdapter();
        adapter.setAllMessages(allMessages, this);
        binding.rvUserChat.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        llm.setSmoothScrollbarEnabled(true);
        binding.rvUserChat.setLayoutManager(llm);

        // on message sent.
        binding.sendBtn.setOnClickListener(view -> {
            AllMessagesModel model = new AllMessagesModel(binding.messageText.getText().toString(), mAuth.getUid(), new Date().getTime());
            binding.messageText.setText("");
            mDatabase.getReference().child("chats").child(senderRoom).child("messages").push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mDatabase.getReference().child("chats")
                            .child(receiverRoom)
                            .child("messages")
                            .push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }
            });
        });
    }

    private void getMessages() {
        mDatabase.getReference().child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (allMessages != null) allMessages.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    AllMessagesModel model = ds.getValue(AllMessagesModel.class);
                    allMessages.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}