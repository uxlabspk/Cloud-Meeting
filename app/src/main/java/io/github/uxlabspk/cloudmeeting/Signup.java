package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    private String meeting_type;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // firebase init
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        Intent parent_Intent = getIntent();
        meeting_type = parent_Intent.getStringExtra("type");

        set_Spinner_Items(meeting_type);

        binding.signupBtn.setOnClickListener(view -> signup());

        binding.alreadyAccountLink.setOnClickListener(view -> {
            Intent i = new Intent(this, Login.class);
            i.putExtra("type", meeting_type);
            startActivity(i);
        });
    }

    private void signup() {
        String fullName = binding.editTextNameAddress.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.userPassword.getText().toString().trim();
        String schoolName = binding.schoolName.getText().toString().trim();
        String schoolClass = binding.sectionName.getText().toString().trim();
        String userRole = binding.userTypes.getSelectedItem().toString();
        String Gender = "Not preferred";

        // Setting the user data
        if (binding.maleGender.isChecked()) {
            Gender = "male";
        } else if (binding.femaleGender.isChecked()) {
            Gender = "female";
        }

        if(!email.matches(emailPattern)){
            binding.emailLayout.setError("Enter a correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            binding.passwordLayout.setError("Enter at least 6 alphanumeric keys for password");
        } else {
            ProgressStatus ps = new ProgressStatus(this);
            ps.setTitle("Performing Registration");
            ps.setCanceledOnTouchOutside(false);
            ps.show();

            String finalGender = Gender;
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ps.dismiss();
                    // Setting data into Firebase Database.
                    final String UUID = mAuth.getCurrentUser().getUid();

                    Users registerUser = new Users(UUID, fullName, email, schoolName, schoolClass, "" , userRole, finalGender);
                    mDatabase.child("Users").child(UUID).child("Profile").setValue(registerUser);

                    if (meeting_type.equals("bus")) {
                        Intent intent = new Intent(Signup.this, business.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    ps.dismiss();
                    Toast.makeText(Signup.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Setting local variable in preferences.
        SharedPreferences pref = getSharedPreferences("User_role", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("User_role", userRole);
        editor.putString("User_School", schoolName);
        editor.putString("User_class", schoolClass);
        editor.apply();
    }

    private void set_Spinner_Items(String meeting_type) {
        if (meeting_type.equals("bus")) {
            String[] testArray = {"Admin", "Employee"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            binding.userTypes.setAdapter(spinnerArrayAdapter);

            binding.schoolLayout.setHint("Organization Name");
            binding.sectionLayout.setHint("Department Name");
        } else {
            String[] testArray = {"Teacher", "Students", "Parent"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_dropdown_item, testArray );
            binding.userTypes.setAdapter(spinnerArrayAdapter);

            binding.schoolLayout.setHint("School Name");
            binding.sectionLayout.setHint("Section Name");
        }
    }
}