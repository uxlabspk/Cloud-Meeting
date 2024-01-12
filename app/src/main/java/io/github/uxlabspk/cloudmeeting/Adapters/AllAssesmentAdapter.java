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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.AssesmentsAddAndRemove;
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
//        holder.assesment_deadline.setText(allAssesments.get(position).getAssesmentDedline());

//        FirebaseStorage.getInstance().getReference().child(allAssesments.get(position).getAssesmentTitle() + "/assesments/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
//            @Override
//            public void onComplete(@NonNull Task<ListResult> task) {
//                long count = task.getResult().getItems().size();
//                holder.assesment_deadline.setText(String.valueOf(count) + " Files");
//            }
//        });
        FirebaseDatabase.getInstance().getReference().child("Assesments").child(allAssesments.get(position).getAssesmentTitle()).child("creation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                holder.assesment_deadline.setText(String.valueOf(count) + " Assesments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // onClick listener
        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, AssesmentsAddAndRemove.class);
            intent.putExtra("className", allAssesments.get(position).getAssesmentTitle());
            context.startActivity(intent);
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
