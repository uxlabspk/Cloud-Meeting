package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.ChatBox;
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

        FirebaseStorage.getInstance().getReference().child(allLectures.get(position).getClass_name_lectures() + "/lectures/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                long count = task.getResult().getItems().size();
                holder.class_lectures_files.setText(String.valueOf(count) + " Files");
            }
        });

        holder.lecture_container.setOnClickListener(view -> {
            Intent intent = new Intent(context, LecturesDetails.class);
            intent.putExtra("className", holder.class_name_lectures.getText());
            intent.putExtra("lecturesUrl", allLectures.get(position).getClass_name_lectures());
            context.startActivity(intent);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views.
            lecture_container = itemView.findViewById(R.id.lecture_container);
            class_name_lectures = itemView.findViewById(R.id.class_name_lectures);
            class_lectures_files = itemView.findViewById(R.id.class_lectures_files);
        }
    }
}
