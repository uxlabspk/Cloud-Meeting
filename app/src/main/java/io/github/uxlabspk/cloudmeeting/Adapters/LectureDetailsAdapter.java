package io.github.uxlabspk.cloudmeeting.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.LecturesDetails;
import io.github.uxlabspk.cloudmeeting.Models.LectureDetailsModel;
import io.github.uxlabspk.cloudmeeting.R;

public class LectureDetailsAdapter extends RecyclerView.Adapter<LectureDetailsAdapter.ViewHolder>{

    private ArrayList<LectureDetailsModel> lectureDetails = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_lecture_details, parent, false);
        LectureDetailsAdapter.ViewHolder viewHolder = new LectureDetailsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lecture_name.setText(lectureDetails.get(position).getLectureFileName());
        // download the file
        holder.lecture_download.setOnClickListener(view -> {
            downloadFile(holder.itemView.getContext(), lectureDetails.get(position).getLectureFileName(), ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), lectureDetails.get(position).getLectureDownloadUrl());
        });
    }

    @Override
    public int getItemCount() {
        return lectureDetails.size();
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

    public void setLectureDetails(ArrayList<LectureDetailsModel> lectureDetails) {
        this.lectureDetails = lectureDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView lecture_details_container;
        private TextView lecture_name;
        private ImageView lecture_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lecture_details_container = itemView.findViewById(R.id.lecture_details_container);
            lecture_name = itemView.findViewById(R.id.lecture_name);
            lecture_download = itemView.findViewById(R.id.lecture_download);
        }
    }
}
