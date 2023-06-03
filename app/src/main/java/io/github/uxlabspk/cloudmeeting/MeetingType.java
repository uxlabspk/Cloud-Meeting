package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MeetingType extends AppCompatActivity {

    private Button btn_edu, btn_bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_type);

        btn_edu = (Button) findViewById(R.id.btn_edu_meeting);
        btn_bus = (Button) findViewById(R.id.btn_bus_meeting);

        btn_bus.setOnClickListener(view -> {
            Intent i = new Intent(MeetingType.this, Login.class);
            i.putExtra("type", "bus");
            startActivity(i);
        });

        btn_edu.setOnClickListener(view -> {
            Intent i = new Intent(MeetingType.this, Login.class);
            i.putExtra("type", "edu");
            startActivity(i);
        });

    }
}