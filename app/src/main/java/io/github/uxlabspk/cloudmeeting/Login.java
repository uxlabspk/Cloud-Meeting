package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private String meeting_type;
    private Spinner user_types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init()
    {
        Intent parent_Intent = getIntent();
        meeting_type = parent_Intent.getStringExtra("type");

        set_Spinner_Items(meeting_type);

        TextView create_account_link = findViewById(R.id.create_account_link);

        create_account_link.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Signup.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });

        // --------------------- HARD CODED LOGIN CREDENTIALS ------------------------------- //
        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(view -> {

            EditText userName = (EditText) findViewById( R.id.signin_user_email);
            EditText userPassword = (EditText) findViewById(R.id.signin_user_password);

            if (userName.getText().toString().equals("naveed@uxlabspk.github.io") && userPassword.getText().toString().equals("test"))
            {
                Toast.makeText(this, "Test login granted!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, "Invalid test login credentials", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void set_Spinner_Items(String meeting_type)
    {
        user_types = (Spinner) findViewById(R.id.user_types);
        if (meeting_type.equals("bus"))
        {
            String[] testArray = {"Admin", "Employee"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            user_types.setAdapter(spinnerArrayAdapter);
        }
        else
        {
            String[] testArray = {"Teacher", "Students", "Parent"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            user_types.setAdapter(spinnerArrayAdapter);
        }
    }
}