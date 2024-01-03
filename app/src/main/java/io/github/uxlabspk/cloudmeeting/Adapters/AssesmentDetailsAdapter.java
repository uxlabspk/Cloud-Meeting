package io.github.uxlabspk.cloudmeeting.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentDetailsModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.ViewAssesmentSubmissions;
import io.github.uxlabspk.cloudmeeting.ViewAssesments;

public class AssesmentDetailsAdapter extends RecyclerView.Adapter<AssesmentDetailsAdapter.ViewHolder> {

    private ArrayList<AssesmentDetailsModel> allAssesments = new ArrayList<>();
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
        holder.assesment_title.setText(allAssesments.get(position).getAssesmentTitle());
        holder.assesment_deadline.setText(allAssesments.get(position).getAssesmentDetail());
        holder.assesment_marks.setText(allAssesments.get(position).getTotalMarks());

        holder.parent.setOnClickListener(view -> {
            // determining the user role.
            SharedPreferences pref = context.getSharedPreferences("User_role", Context.MODE_PRIVATE);
            String userRole = pref.getString("User_role", null);

            if (userRole.equals("Teacher")) {
                Intent intent = new Intent(context, ViewAssesmentSubmissions.class);
                intent.putExtra("className", pref.getString("ClassName", null).toString());
                intent.putExtra("AssesmentTitle", allAssesments.get(position).getAssesmentTitle().toString());
                intent.putExtra("AssesmentDetails", allAssesments.get(position).getAssesmentDetail().toString());
                intent.putExtra("AssesmentDeadline", allAssesments.get(position).getAssesmentDeadline().toString());
                intent.putExtra("AssesmentMarks", allAssesments.get(position).getTotalMarks().toString());
                context.startActivity(intent);
            } else if (userRole.equals("Students")){
                Intent intent = new Intent(context, ViewAssesments.class);
                intent.putExtra("className", pref.getString("ClassName", null).toString());
                intent.putExtra("AssesmentTitle", allAssesments.get(position).getAssesmentTitle().toString());
                intent.putExtra("AssesmentDetails", allAssesments.get(position).getAssesmentDetail().toString());
                intent.putExtra("AssesmentDeadline", allAssesments.get(position).getAssesmentDeadline().toString());
                intent.putExtra("AssesmentMarks", allAssesments.get(position).getTotalMarks().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allAssesments.size();
    }

    public void setAllAssesments(ArrayList<AssesmentDetailsModel> allAssesments, Context context) {
        this.allAssesments = allAssesments;
        this.context = context;
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
