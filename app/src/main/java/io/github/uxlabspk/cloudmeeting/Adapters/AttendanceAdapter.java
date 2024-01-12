package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.Models.AttendanceModel;
import io.github.uxlabspk.cloudmeeting.Models.AttendanceViewModel;
import io.github.uxlabspk.cloudmeeting.R;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private ArrayList<AttendanceViewModel> attendance = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_assesments_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assesment_title.setText(attendance.get(position).getStudentName());
        holder.assesment_deadline.setText(attendance.get(position).getClassName());
        holder.assesment_marks.setText(attendance.get(position).getAttendanceCount());
    }

    @Override
    public int getItemCount() {
        return attendance.size();
    }

    public void setAttendance(ArrayList<AttendanceViewModel> attendance) {
        this.attendance = attendance;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assesment_title;
        private TextView assesment_deadline;
        private TextView assesment_marks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assesment_title = (TextView) itemView.findViewById(R.id.assesment_title);
            assesment_deadline = (TextView) itemView.findViewById(R.id.assesment_deadline);
            assesment_marks = (TextView) itemView.findViewById(R.id.assesment_marks);
        }
    }
}
