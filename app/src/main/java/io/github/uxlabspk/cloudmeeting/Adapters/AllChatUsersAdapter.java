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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.ChatBox;
import io.github.uxlabspk.cloudmeeting.Classes.ConfirmDialog;
import io.github.uxlabspk.cloudmeeting.Classes.Type;
import io.github.uxlabspk.cloudmeeting.JoinClass;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.R;

public class AllChatUsersAdapter extends RecyclerView.Adapter<AllChatUsersAdapter.ViewHolder> {

    private ArrayList<AllChatUsersModel> allChatUsers = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_chat_users, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting date and message
        holder.user_full_name.setText(allChatUsers.get(position).getUserName());
        holder.user_last_message.setText(allChatUsers.get(position).getUserLastMessage());

        // last seen
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        long time = allChatUsers.get(position).getLastSeen();
        holder.user_last_seen.setText(formatter.format(new Time(Long.parseLong(time + ""))));

        // intent call
        holder.container.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatBox.class);
            intent.putExtra("userName", holder.user_full_name.getText());
            intent.putExtra("availability", "offline");
            context.startActivity(intent);
        });

        holder.container.setOnLongClickListener(view -> {

            ConfirmDialog cd = new ConfirmDialog(context, Type.CONFIRM);
            cd.setCanceledOnTouchOutside(false);
            cd.setDialog_headline("Confirm to Delete");
            cd.setDialog_body("Are you sure to delete the chat?");
            cd.setYes_btn_text("Delete");
            cd.setNo_btn_text("Cancel");

            // yes button click listener
            cd.getYes_btn().setOnClickListener(view1 -> {
                // delete the data here.
                allChatUsers.remove(position);
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

    @Override
    public int getItemCount() {
        return allChatUsers.size();
    }

    public void setAllChatUsers(ArrayList<AllChatUsersModel> allChatUsers, Context context) {
        this.allChatUsers = allChatUsers;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView container;
        private ImageView user_avatar;
        private TextView user_full_name;
        private TextView user_last_message;
        private TextView user_last_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.chat_user_container);
            user_avatar = itemView.findViewById(R.id.user_avatar);
            user_full_name = itemView.findViewById(R.id.user_full_name);
            user_last_message = itemView.findViewById(R.id.user_last_message);
            user_last_seen = itemView.findViewById(R.id.user_last_seen);
        }
    }
}
