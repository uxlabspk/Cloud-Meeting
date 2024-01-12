package io.github.uxlabspk.cloudmeeting;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.LectureDetailsAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.Models.LectureDetailsModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityLecturesDetailsBinding;

public class LecturesDetails extends AppCompatActivity {
    private ActivityLecturesDetailsBinding binding;
    private String lectureUrl;
    StorageReference storageReference;
    ProgressStatus progressStatus;
    Uri pdfUrl;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLecturesDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // getting data from intent
        binding.lectureTitle.setText(getIntent().getStringExtra("className"));


        // determining the user role.
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userRole = pref.getString("User_role", null);

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            binding.addlectures.setVisibility(View.VISIBLE);

            // adding lecture files.
            binding.addlectures.setOnClickListener(view -> {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/*");
                startActivityForResult(galleryIntent, 1);
            });

        } else {
            binding.addlectures.setVisibility(View.GONE);
        }

        // get lectures
        getLectureFile();
    }

    private void getLectureFile() {
        // Ready the firebase.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Ready the Recycler View
        ArrayList<LectureDetailsModel> lectureDetails = new ArrayList<>();

        binding.notFound.setVisibility(View.VISIBLE);

        LectureDetailsModel model = new LectureDetailsModel();
        // getting lecture files.
        storageReference.child(getIntent().getStringExtra("className") + "/lectures/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).listAll().addOnSuccessListener(listResult -> {
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl = uri.toString();
                        model.setLectureDownloadUrl(downloadUrl);
                    }
                });
                if (!lectureDetails.isEmpty()) lectureDetails.clear();
                model.setLectureFileName(item.getName());
                lectureDetails.add(model);
                binding.notFound.setVisibility(View.GONE);
            }

            LectureDetailsAdapter adapter = new LectureDetailsAdapter();
            adapter.setLectureDetails(lectureDetails);
            adapter.notifyDataSetChanged();
            binding.lectureFiles.setAdapter(adapter);
            binding.lectureFiles.setLayoutManager(new LinearLayoutManager(LecturesDetails.this));

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Here we are initialising the progress dialog box
            progressStatus = new ProgressStatus(this);
            progressStatus.setTitle("Uploading");
            progressStatus.setCanceledOnTouchOutside(true);

            // this will show message uploading
            progressStatus.show();
            pdfUrl = data.getData();

            File f = new File(pdfUrl.getLastPathSegment().toString());

            StorageReference filepath = storageReference.child(getIntent().getStringExtra("className") + "/lectures/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + f.getName());// .replace(".pdf", "") + ".pdf");

            filepath.putFile(pdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            lectureUrl = uri.toString();
                            progressStatus.dismiss();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            progressStatus.dismiss();
                            Toast.makeText(LecturesDetails.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            getLectureFile();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressStatus.dismiss();
                            Toast.makeText(LecturesDetails.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}