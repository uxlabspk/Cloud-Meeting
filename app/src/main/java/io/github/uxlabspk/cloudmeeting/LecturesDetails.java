package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class LecturesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures_details);

        init();
    }

    private void init() {
        // goBack
        ImageView goBack = (ImageView) findViewById(R.id.goBack);

        goBack.setOnClickListener(view -> onBackPressed());
    }
}