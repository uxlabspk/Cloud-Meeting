package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private String meeting_type;
    private Spinner user_Roles;

    private EditText userName;

    private EditText userPassword;

    private String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

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

        // set_Spinner_Items(meeting_type);

        TextView create_account_link = (TextView) findViewById(R.id.create_account_link);

        create_account_link.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Signup.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });

        TextView resetPassword = (TextView) findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener(view -> {
            EditText userName = (EditText) findViewById(R.id.signin_user_email);
            // user reset link.
            if (!userName.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Check your email for reset link!", Toast.LENGTH_LONG).show();
                // TODO : Send user password reset link
            }
            else
            {
                Toast.makeText(this, "Please enter your email for reset link", Toast.LENGTH_LONG).show();
            }
        });

        // --------------------- HARD CODED LOGIN CREDENTIALS ------------------------------- //
        Button login_button = (Button) findViewById(R.id.login_button);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // firebase login click event
        /* public void onClick(View v)
        {
            performLogin();
        }*/
        login_button.setOnClickListener(v -> {
            Intent newIntent = new Intent(Login.this, MainActivity.class);
            startActivity(newIntent);
        });
    }

    private void performLogin()
    {
        EditText userName = (EditText) findViewById( R.id.signin_user_email);
        EditText userPassword = (EditText) findViewById(R.id.signin_user_password);

        String email = userName.getText().toString();
        String password = userPassword.getText().toString();

        if(!email.matches(emailPattern)){
            userName.setError("Enter a correct Email");
        } else if (password.isEmpty() || password.length()<6)
        {
            userPassword.setError("Enter at least 6 alphanumeric keys for password");
        } else{
            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                        //sendUserToNextActivity();
                        Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void set_Spinner_Items(String meeting_type)
    {
        user_Roles = (Spinner) findViewById(R.id.user_types);
        if (meeting_type.equals("bus"))
        {
            String[] userRolesArray = {"Admin", "Employee"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, userRolesArray );
            user_Roles.setAdapter(spinnerArrayAdapter);
        }
        else
        {
            String[] userRolesArray = {"Teacher", "Students", "Parent"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, userRolesArray );
            user_Roles.setAdapter(spinnerArrayAdapter);
        }
    }
}