package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileEdit extends AppCompatActivity {

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        init();
    }

    private void init() {
        // goBack
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> onBackPressed());

        saveBtn = (Button) findViewById(R.id.save_changes);


        saveBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Saving changes...", Toast.LENGTH_LONG).show();
        });


    }
}