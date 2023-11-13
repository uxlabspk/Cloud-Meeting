package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.LecturesDetails;
import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.R;

public class AllLecturesAdapter extends RecyclerView.Adapter<AllLecturesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<AllLecturesModel> allLectures = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_lectures, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.class_name_lectures.setText(allLectures.get(position).getClass_name_lectures());
//        holder.class_lectures_files.setText(allLectures.get(position).getClass_lectures_files_count());

        // download all files as zip
        holder.class_lectures_files_download.setOnClickListener(view -> {
            ProgressStatus ps = new ProgressStatus(context);
            ps.setTitle("downloading...");
            ps.show();
            // other logic goes here.
        });

        holder.lecture_container.setOnClickListener(view -> {
            context.startActivity(new Intent(context, LecturesDetails.class));
        });
    }

    @Override
    public int getItemCount() {
        return allLectures.size();
    }

    public void setAllLectures(ArrayList<AllLecturesModel> allLectures, Context context) {
        this.allLectures = allLectures;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView lecture_container;
        private TextView class_name_lectures;
        private TextView class_lectures_files;
        private ImageView class_lectures_files_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views.
            lecture_container = itemView.findViewById(R.id.lecture_container);
            class_name_lectures = itemView.findViewById(R.id.class_name_lectures);
            class_lectures_files = itemView.findViewById(R.id.class_lectures_files);
            class_lectures_files_download = itemView.findViewById(R.id.class_lectures_files_download);
        }
    }
}
