package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

public class ChatBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        ImageView back = findViewById(R.id.goBack);

        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}