package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    private String meeting_type;

    private String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
    private ProgressStatus progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        Intent parent_Intent = getIntent();
        meeting_type = parent_Intent.getStringExtra("type");

        // ready the firebase.
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // creating click event listeners
        binding.createAccountLink.setOnClickListener(view -> {
            Intent i = new Intent(Login.this, Signup.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });

        binding.resetPassword.setOnClickListener(view -> {
            EditText userName = (EditText) findViewById(R.id.signin_user_email);
            if (!userName.getText().toString().isEmpty()) {
                mAuth.sendPasswordResetEmail(userName.getText().toString()).addOnCompleteListener(task -> {
                  if (task.isSuccessful()) {
                      Toast.makeText(Login.this, "Check your email for reset link!", Toast.LENGTH_SHORT).show();
                  } else {
                      Toast.makeText(Login.this, "Error : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                  }
                });
            } else {
                Toast.makeText(this, "Please enter your email for reset link", Toast.LENGTH_LONG).show();
            }
        });

        progressDialog = new ProgressStatus(this);

        // mUser = mAuth.getCurrentUser();

        binding.loginButton.setOnClickListener(view -> {
            performLogin();
        });
    }

    private void performLogin() {
        String email = binding.signinUserEmail.getText().toString();
        String password = binding.signinUserPassword.getText().toString();

        if(!email.matches(emailPattern)) {
            binding.emailLayout.setError("Enter a correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.passwordLayout.setError("Enter at least 6 digits for password");
        } else {
            binding.emailLayout.setErrorEnabled(false);
            binding.passwordLayout.setErrorEnabled(false);

            progressDialog.setTitle("Performing Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    // Determining the user role on login.
                    mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users userDetails = snapshot.getValue(Users.class);
                            SharedPreferences pref = getSharedPreferences("User_role", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("User_role", userDetails.getUserRole().toString());
                            editor.putString("User_School", userDetails.getUserSchool().toString());
                            editor.putString("User_class", userDetails.getUserClass().toString());
                            editor.apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Login.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    progressDialog.dismiss();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                     Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}