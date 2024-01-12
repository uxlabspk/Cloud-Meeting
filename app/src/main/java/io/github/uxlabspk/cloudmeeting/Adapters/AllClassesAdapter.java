package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.JoinClass;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.R;

public class AllClassesAdapter extends RecyclerView.Adapter<AllClassesAdapter.ViewHolder>{

    private ArrayList<AllClassesModel> allClasses = new ArrayList<>();
    private Context context;

    public void setAllClasses(ArrayList<AllClassesModel> allClasses, Context context) {
        this.allClasses = allClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_classes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting class name and schedule
        holder.class_name.setText(allClasses.get(position).getClass_name());
        holder.class_shedule.setText(allClasses.get(position).getTeacher_id());

        // class time
        holder.class_start_time.setText(allClasses.get(position).getClass_start_time());

        // starting class intent
        holder.class_container.setOnClickListener(view -> {
            Intent intent = new Intent(context, JoinClass.class);
            intent.putExtra("RoomName", holder.class_name.getText().toString());
            context.startActivity(intent);
        });

        // determining the user role.
        SharedPreferences pref = context.getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userRole = pref.getString("User_role", null);

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            // on long hold
            holder.class_container.setOnLongClickListener(view -> {
                ConfirmDialog cd = new ConfirmDialog(context, Type.CONFIRM);
                cd.setCanceledOnTouchOutside(false);
                cd.setDialog_headline("Confirm to Delete");
                cd.setDialog_body("Are you sure to delete this class?");
                cd.setYes_btn_text("Delete");
                cd.setNo_btn_text("Cancel");

                // yes button click listener
                cd.getYes_btn().setOnClickListener(view1 -> {
                    // delete the data here.
                    allClasses.remove(position);
                    FirebaseDatabase.getInstance().getReference().child("Class").child(holder.class_name.getText().toString()).removeValue();
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
        return allClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView class_container;
        private TextView class_name;
        private TextView class_shedule;
        private TextView class_start_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views
            class_container = itemView.findViewById(R.id.class_container);
            class_name = itemView.findViewById(R.id.class_name);
            class_shedule = itemView.findViewById(R.id.class_shedule);
            class_start_time = itemView.findViewById(R.id.class_start_time);
        }
    }
}
