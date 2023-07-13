package io.github.uxlabspk.cloudmeeting;


import static io.github.uxlabspk.cloudmeeting.Classes.PaintView.colorList;
import static io.github.uxlabspk.cloudmeeting.Classes.PaintView.currentBrush;
import static io.github.uxlabspk.cloudmeeting.Classes.PaintView.pathList;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

import io.github.uxlabspk.cloudmeeting.databinding.ActivityDrawingBinding;

public class DrawingActivity extends AppCompatActivity {
    ActivityDrawingBinding binding;
    public static Path path = new Path();
    public static Paint brush = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void init() {
        binding.pencil.setOnClickListener(view -> {
            brush.setColor(Color.BLACK);
            currentColor(brush.getColor());
        });
        binding.eraser.setOnClickListener(view -> {
            pathList.clear();
            colorList.clear();
            path.reset();
        });
        binding.red.setOnClickListener(view -> {
            brush.setColor(Color.parseColor("#FF0051"));
            currentColor(brush.getColor());
        });
        binding.yellow.setOnClickListener(view -> {
            brush.setColor(Color.parseColor("#FFE500"));
            currentColor(brush.getColor());
        });
        binding.green.setOnClickListener(view -> {
            brush.setColor(Color.parseColor("#00BC4B"));
            currentColor(brush.getColor());
        });
        binding.purple.setOnClickListener(view -> {
            brush.setColor(Color.parseColor("#3F51B5"));
            currentColor(brush.getColor());
        });
        binding.blue.setOnClickListener(view -> {
            brush.setColor(Color.parseColor("#0080ff"));
            currentColor(brush.getColor());
        });
    }
    public void currentColor(int c) {
        currentBrush = c;
        path = new Path();
    }
}