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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Classes.TimeFormatter;
import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.ViewAssesments;

public class AllAssesmentAdapter extends RecyclerView.Adapter<AllAssesmentAdapter.ViewHolder> {

    private ArrayList<AllAssesmentsModel> allAssesments = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_assesments, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assesment_title.setText(allAssesments.get(position).getAssesmentTitle());
        holder.assesment_deadline.setText(allAssesments.get(position).getAssesmentDedline());

        // Assessment Publish Time
        TimeFormatter timeFormatter = new TimeFormatter(allAssesments.get(position).getAssesmentPublishTime());
        holder.assesment_notification_time.setText(timeFormatter.formattedTime());

        // onClick listener
        holder.parent.setOnClickListener(view -> {
            context.startActivity(new Intent(context, ViewAssesments.class));
        });
    }

    @Override
    public int getItemCount() {
        return allAssesments.size();
    }

    public void setAllAssesments(ArrayList<AllAssesmentsModel> allAssesments, Context context) {
        this.allAssesments = allAssesments;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView assesment_title;
        private TextView assesment_deadline;
        private TextView assesment_notification_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = (CardView) itemView.findViewById(R.id.parent);
            assesment_title = (TextView) itemView.findViewById(R.id.assesment_title);
            assesment_deadline = (TextView) itemView.findViewById(R.id.assesment_deadline);
            assesment_notification_time = (TextView) itemView.findViewById(R.id.assesment_notification_time);
        }
    }
}
