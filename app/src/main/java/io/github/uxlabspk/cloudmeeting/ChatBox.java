package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        init();
    }

    private void init() {
        // goBack
        ImageView back = findViewById(R.id.goBack);
        back.setOnClickListener(view -> onBackPressed());

        // getting data from intent
        String userName = getIntent().getStringExtra("userName");
        String availability = getIntent().getStringExtra("availability");
        TextView userNameText = findViewById(R.id.userName);
        TextView userActiveText = findViewById(R.id.userActive);
        userNameText.setText(userName);
        userActiveText.setText(availability);
    }
}