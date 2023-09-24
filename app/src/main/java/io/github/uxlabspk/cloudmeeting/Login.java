package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private String meeting_type;

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

    private void init() {
        // goBack
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> onBackPressed());

        Intent parent_Intent = getIntent();
        meeting_type = parent_Intent.getStringExtra("type");

        // finding views
        TextView create_account_link = (TextView) findViewById(R.id.create_account_link);
        Button login_button = (Button) findViewById(R.id.login_button);
        TextView resetPassword = (TextView) findViewById(R.id.resetPassword);

        // creating click event listeners
        create_account_link.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Signup.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });

        resetPassword.setOnClickListener(view -> {
            EditText userName = (EditText) findViewById(R.id.signin_user_email);
            if (!userName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Check your email for reset link!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter your email for reset link", Toast.LENGTH_LONG).show();
            }
        });

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login_button.setOnClickListener(view -> {
//            performLogin();

            // Storing user login state. TODO: Remove test here.
            SharedPreferences userDetails = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
            SharedPreferences.Editor editor = userDetails.edit();
            editor.putBoolean("IS_LOGIN", true);
            editor.apply();
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        });
    }

    private void performLogin() {
        TextInputLayout emailLayout = findViewById(R.id.email_layout);
        TextInputLayout passwordLayout = findViewById(R.id.password_layout);
        EditText userName = (EditText) findViewById( R.id.signin_user_email);
        EditText userPassword = (EditText) findViewById(R.id.signin_user_password);

        String email = userName.getText().toString();
        String password = userPassword.getText().toString();

        if(!email.matches(emailPattern)) {
            emailLayout.setError("Enter a correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            passwordLayout.setError("Enter at least 6 digits for password");
        } else {
            emailLayout.setErrorEnabled(false);
            passwordLayout.setErrorEnabled(false);

            progressDialog.setMessage("Please wait while Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    // Storing user login state.
                    SharedPreferences userDetails = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putBoolean("IS_LOGIN", true);
                    editor.apply();

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();

                    if (mAuth.getCurrentUser().isEmailVerified()) {

                    } else {
                        Toast.makeText(Login.this, "Verify your email", Toast.LENGTH_LONG).show();
                    }

                } else {
                    progressDialog.dismiss();
                     Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}