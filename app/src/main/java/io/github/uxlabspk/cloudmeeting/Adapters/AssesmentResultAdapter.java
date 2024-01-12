package io.github.uxlabspk.cloudmeeting.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.MarkAssesments;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentResultModel;
import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.R;

public class AssesmentResultAdapter extends RecyclerView.Adapter<AssesmentResultAdapter.ViewHolder> {

    private ArrayList<SubmissionModel> allAssesmentResult = new ArrayList<>();
    private Context context;
    private String className;
    private String assessmentTitle;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lecture_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(allAssesmentResult.get(position).getStudentID()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    holder.lecture_name.setText(user.getUserName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, MarkAssesments.class);
            intent.putExtra("StudentID", holder.lecture_name.getText().toString());
            intent.putExtra("className", className);
            intent.putExtra("assessmentTitle", assessmentTitle);
            intent.putExtra("uid", allAssesmentResult.get(position).getStudentID());
            context.startActivity(intent);
        });

        // download the submission
        holder.lecture_download.setOnClickListener(view -> {
            downloadFile(context, holder.lecture_name.getText().toString(), ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), allAssesmentResult.get(position).getSubmissionUrl());
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


    public void setAllAssesmentResult(ArrayList<SubmissionModel> allAssesmentResult, Context context, String className, String assessmentTitle) {
        this.allAssesmentResult = allAssesmentResult;
        this.context = context;
        this.className = className;
        this.assessmentTitle = assessmentTitle;
    }

    @Override
    public int getItemCount() {
        return allAssesmentResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView lecture_name;
        private ImageView lecture_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding the views
            parent = itemView.findViewById(R.id.lecture_details_container);
            lecture_name = itemView.findViewById(R.id.lecture_name);
            lecture_download = itemView.findViewById(R.id.lecture_download);
        }
    }
}
