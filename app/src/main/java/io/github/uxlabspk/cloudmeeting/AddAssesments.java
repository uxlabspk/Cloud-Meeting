package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import io.github.uxlabspk.cloudmeeting.Models.AssesmentDetailsModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityAddAssesmentsBinding;

public class AddAssesments extends AppCompatActivity {

    private ActivityAddAssesmentsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference storageReference;
    private ProgressDialog dialog;

    private AssesmentDetailsModel model;
    Uri pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAssesmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ready the model
        model = new AssesmentDetailsModel();
        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // select the file
        binding.attachmentBtn.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // We will be redirected to choose pdf
            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });

        // publish the assesment
        binding.publishBtn.setOnClickListener(view -> {
            publishAssesment();
        });
    }

    private void publishAssesment() {
        // getting the input from fields
        if (!binding.assesmentTitle.getText().toString().isEmpty() || !binding.assesmentDeadline.getText().toString().isEmpty()) {
            model.setAssesmentTitle(binding.assesmentTitle.getText().toString());
            model.setAssesmentDetail(binding.assesmentDetails.getText().toString());
            model.setAssesmentDeadline(binding.assesmentDeadline.getText().toString());
            model.setTotalMarks(binding.assesmentMarks.getText().toString());
            model.setClassName(getIntent().getStringExtra("ClassName"));

            mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("ClassName")).child("creation").child(binding.assesmentTitle.getText().toString().replaceAll(" ", "")).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddAssesments.this, "Successfully Created the Assessment!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
            binding.assesmentTitleLayout.setError("Required!");
            binding.assesmentDeadlineLayout.setError("Required!");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            pdfUrl = data.getData();

            StorageReference filepath = storageReference.child(getIntent().getStringExtra("ClassName").toString() + "/assesments/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + binding.assesmentTitle.getText().toString() + ".pdf");

            filepath.putFile(pdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dialog.dismiss();
                            model.setAttachmentUrl(uri.toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            dialog.dismiss();
                            Toast.makeText(AddAssesments.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(AddAssesments.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

//            filepath.putFile(pdfUrl).continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return filepath.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        // After uploading is done it progress
//                        // dialog box will be dismissed
//                        dialog.dismiss();
//                        Toast.makeText(AddAssesments.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        dialog.dismiss();
//                        Toast.makeText(AddAssesments.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }
}