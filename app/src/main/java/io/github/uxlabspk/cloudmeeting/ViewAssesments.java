package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class ViewAssesments extends AppCompatActivity {

    Button save_btn;
    TextView timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assesments);

        init();

    }

    private void init() {
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> onBackPressed());

        save_btn = (Button) findViewById(R.id.save_btn);
        timeStamp = (TextView) findViewById(R.id.assesment_save_time);

        Date d = new Date();

        String time = "Submittion time " + d.getHours() + ":" + d.getMinutes();;

        save_btn.setOnClickListener(view -> timeStamp.setText(time));
    }
}