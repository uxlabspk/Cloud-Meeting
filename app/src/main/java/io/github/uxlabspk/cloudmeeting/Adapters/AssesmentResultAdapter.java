package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Models.AssesmentResultModel;
import io.github.uxlabspk.cloudmeeting.QuizResults;
import io.github.uxlabspk.cloudmeeting.R;

public class AssesmentResultAdapter extends RecyclerView.Adapter<AssesmentResultAdapter.ViewHolder> {

    private ArrayList<AssesmentResultModel> allAssesmentResult = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_assesments_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assesment_title.setText(allAssesmentResult.get(position).getTitle());
        holder.assesment_deadline.setText(allAssesmentResult.get(position).getStudentName());

        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, QuizResults.class);
            intent.putExtra("Title", allAssesmentResult.get(position).getTitle());
            intent.putExtra("StudentName", allAssesmentResult.get(position).getStudentMarks());
            context.startActivity(intent);
        });
    }

    public void setAllAssesmentResult(ArrayList<AssesmentResultModel> allAssesmentResult) {
        this.allAssesmentResult = allAssesmentResult;
    }

    @Override
    public int getItemCount() {
        return allAssesmentResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView assesment_title;
        private TextView assesment_deadline;
        private TextView assesment_marks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding the views
            parent = itemView.findViewById(R.id.parent);
            assesment_title = itemView.findViewById(R.id.assesment_title);
            assesment_deadline = itemView.findViewById(R.id.assesment_deadline);
            assesment_marks = itemView.findViewById(R.id.assesment_marks);
        }
    }
}
