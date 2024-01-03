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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.uxlabspk.cloudmeeting.databinding.ActivityViewAssesmentsBinding;

public class ViewAssesments extends AppCompatActivity {

    private ActivityViewAssesmentsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private StorageReference storageReference;

    private ProgressDialog dialog;
    Uri pdfUrl;

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

//        // download the file
//        binding.downloadFile.setOnClickListener(view -> {
//            storageReference.child("programming" + "/assesments/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + binding.assesmentTitle.getText().toString() + ".pdf").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    // Context context, String fileName, String fileExtension, String destinationDirectory, String url
//                    downloadFile(ViewAssesments.this, binding.assesmentTitle.getText().toString(), ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), task.getResult().toString());
//
//                }
//            });
//        });

        // select the submission
        binding.filePick.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // We will be redirected to choose pdf
            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });
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

            StorageReference filepath = storageReference.child(getIntent().getStringExtra("ClassName") + "/assesments/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/submission/" + binding.assesmentTitle.getText().toString() + ".pdf");
            filepath.putFile(pdfUrl).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Toast.makeText(ViewAssesments.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(ViewAssesments.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}