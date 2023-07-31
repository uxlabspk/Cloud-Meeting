package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Signup extends AppCompatActivity {

    private String meeting_type;
    private Spinner user_types;
    private TextView already_account_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }


    private void init() {
        Intent parent_Intent = getIntent();
        meeting_type = parent_Intent.getStringExtra("type");

        set_Spinner_Items(meeting_type);

        already_account_link = (TextView) findViewById(R.id.already_account_link);

        already_account_link.setOnClickListener(view -> {
            Intent i = new Intent(Signup.this, Login.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });
    }

    private void set_Spinner_Items(String meeting_type) {
        user_types = (Spinner) findViewById(R.id.user_types);
        if (meeting_type.equals("bus")) {
            String[] testArray = {"Admin", "Employee"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            user_types.setAdapter(spinnerArrayAdapter);
        } else {
            String[] testArray = {"Teacher", "Students", "Parent"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            user_types.setAdapter(spinnerArrayAdapter);
        }
    }

}