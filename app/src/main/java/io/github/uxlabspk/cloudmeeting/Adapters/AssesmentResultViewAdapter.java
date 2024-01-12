package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.AssesmentDetails;
import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentDetailsModel;
import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.ViewAssesmentSubmissions;
import io.github.uxlabspk.cloudmeeting.ViewAssesments;

public class AssesmentResultViewAdapter extends RecyclerView.Adapter<AssesmentResultViewAdapter.ViewHolder> {
    private ArrayList<AssesmentDetailsModel> allAssesments = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_assesments_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assesment_title.setText(allAssesments.get(position).getAssesmentTitle());
        holder.assesment_deadline.setText(allAssesments.get(position).getClassName());
        holder.assesment_marks.setText(allAssesments.get(position).getTotalMarks());

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, AssesmentDetails.class);
            intent.putExtra("Title", allAssesments.get(position).getAssesmentTitle());
            intent.putExtra("Class", allAssesments.get(position).getClassName());
            intent.putExtra("TotalMarks", allAssesments.get(position).getTotalMarks());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return allAssesments.size();
    }

    public void setAllAssesments(ArrayList<AssesmentDetailsModel> allAssesments) {
        this.allAssesments = allAssesments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView assesment_title;
        private TextView assesment_deadline;
        private TextView assesment_marks;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            assesment_title = itemView.findViewById(R.id.assesment_title);
            assesment_deadline = itemView.findViewById(R.id.assesment_deadline);
            assesment_marks = itemView.findViewById(R.id.assesment_marks);
        }
    }
}
