package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class QuizResults extends AppCompatActivity {

    CardView prototype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        init();
    }

    private void init() {
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> onBackPressed());

        prototype = (CardView) findViewById(R.id.prototype_quiz1_result_box);
        // click listner for quiz/assignments details.
        prototype.setOnClickListener(view -> startActivity(new Intent(this, AssesmentDetails.class)));
    }
}