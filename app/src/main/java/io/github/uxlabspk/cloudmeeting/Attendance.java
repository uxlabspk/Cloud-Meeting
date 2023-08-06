package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Attendance extends AppCompatActivity {

    TextView view_attendance_details;
    CardView attendance_details_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        init();
    }

    private void init() {
        // goBack
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> onBackPressed());

        view_attendance_details = (TextView) findViewById(R.id.view_details);
        attendance_details_card = (CardView) findViewById(R.id.attendance_details_card);

        // set the attendance details card visibility.
        view_attendance_details.setOnClickListener(view -> {
            if (attendance_details_card.getVisibility() == View.GONE) {
                attendance_details_card.setVisibility(View.VISIBLE);
            } else {
                attendance_details_card.setVisibility(View.GONE);
            }
        });
    }
}