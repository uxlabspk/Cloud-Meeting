package io.github.uxlabspk.cloudmeeting.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.LecturesDetails;
import io.github.uxlabspk.cloudmeeting.Models.LectureDetailsModel;
import io.github.uxlabspk.cloudmeeting.R;

public class LectureDetailsAdapter extends RecyclerView.Adapter<LectureDetailsAdapter.ViewHolder>{

    private ArrayList<LectureDetailsModel> lectureDetails = new ArrayList<>();
    private FirebaseStorage storage;

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


        // determining the user role.
        SharedPreferences pref = holder.itemView.getContext().getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userRole = pref.getString("User_role", null);

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            // ready the storage
            storage = FirebaseStorage.getInstance();

            holder.lecture_details_container.setOnLongClickListener(view -> {
                StorageReference storageRef = storage.getReferenceFromUrl(lectureDetails.get(position).getLectureDownloadUrl());

                ConfirmDialog cd = new ConfirmDialog(holder.itemView.getContext(), Type.CONFIRM);
                cd.setCanceledOnTouchOutside(false);
                cd.setDialog_headline("Confirm to Delete");
                cd.setDialog_body("Are you sure to delete this class?");
                cd.setYes_btn_text("Delete");
                cd.setNo_btn_text("Cancel");

                // yes button click listener
                cd.getYes_btn().setOnClickListener(view1 -> {
                    // delete the data here.
                    lectureDetails.remove(position);

                    // Delete the file
                    storageRef.delete().addOnSuccessListener(aVoid -> {
                                // File deleted successfully
                                Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(exception -> {
                                // Uh-oh, an error occurred!
                                Toast.makeText(holder.itemView.getContext(), "Error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                    notifyDataSetChanged();
                    cd.dismiss();
                });

                // no button click listener
                cd.getNo_btn().setOnClickListener(view2 -> {
                    cd.dismiss();
                });

                cd.show();

                return false;
            });
        }
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
