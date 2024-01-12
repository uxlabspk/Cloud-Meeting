package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityViewAssesmentsBinding;

public class ViewAssesments extends AppCompatActivity {

    private ActivityViewAssesmentsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference storageReference;
    private ProgressDialog dialog;
    Uri pdfUrl;
    private SubmissionModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAssesmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // setting data
        binding.newQuizes.setText(getIntent().getStringExtra("AssesmentTitle"));
        binding.assesmentTitle.setText(getIntent().getStringExtra("AssesmentTitle"));
        binding.assesmentDeadline.setText(getIntent().getStringExtra("AssesmentDeadline"));
        binding.assesmentDetails.setText(getIntent().getStringExtra("AssesmentDetails"));
        binding.assesmentStatus.setText(getIntent().getStringExtra("AssesmentMarks") + " Points");

        // check whether the assessment is overdue
        String assignmentDueDate = binding.assesmentDeadline.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dueDate = dateFormat.parse(assignmentDueDate);
            Date currentDate = new Date();
            currentDate = removeTime(currentDate);

            if (currentDate.before(dueDate)) {
                binding.uploadFile.setVisibility(View.VISIBLE);
                binding.filePick.setVisibility(View.VISIBLE);
            } else if (currentDate.equals(dueDate)) {
                binding.uploadFile.setVisibility(View.VISIBLE);
                binding.filePick.setVisibility(View.VISIBLE);
            } else {
//                binding.uploadFile.setVisibility(View.GONE);
//                binding.filePick.setVisibility(View.GONE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // check whether the assessment is already submitted
//        checkAvailability();

        // ready the model
        model = new SubmissionModel();

        // download the file
        binding.downloadFile.setOnClickListener(view -> {
            downloadFile(this, getIntent().getStringExtra("AssesmentTitle"), ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), getIntent().getStringExtra("AssessmentUrl"));
        });

        // select the submission
        binding.filePick.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // We will be redirected to choose pdf
            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });

        // upload the submission
        binding.uploadFile.setOnClickListener(view -> {
            model.setStudentID(mAuth.getCurrentUser().getUid());
            model.setStudentRemarks("");
            model.setSubmited(true);
            model.setStudentMarks("");

            mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("className")).child("submission").child(getIntent().getStringExtra("AssesmentTitle").replaceAll(" ", "")).child(mAuth.getCurrentUser().getUid()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ViewAssesments.this, "Successfully submitted", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void checkAvailability() {
        mDatabase.getReference().child("Assesments").child(getIntent().getStringExtra("className")).child("submission").child(getIntent().getStringExtra("AssesmentTitle").replaceAll(" ", "")).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SubmissionModel model1 = snapshot.getValue(SubmissionModel.class);
                    if (model1.isSubmited()) {
                        binding.uploadFile.setVisibility(View.GONE);
                        binding.filePick.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    // Helper method to remove time from a Date object
    private static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
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

            StorageReference filepath = storageReference.child(getIntent().getStringExtra("className").toString() + "/assesments/" + "/submission/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + binding.assesmentTitle.getText().toString() + ".pdf");

            filepath.putFile(pdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dialog.dismiss();
                            model.setSubmissionUrl(uri.toString());
                            // download url
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            dialog.dismiss();
                            Toast.makeText(ViewAssesments.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(ViewAssesments.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}