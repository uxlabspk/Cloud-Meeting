package io.github.uxlabspk.cloudmeeting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Classes.TimeFormatter;
import io.github.uxlabspk.cloudmeeting.Models.AllMessagesModel;
import io.github.uxlabspk.cloudmeeting.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private ArrayList<AllMessagesModel> allMessages = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chat_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllMessagesModel message = allMessages.get(position);

        if (message.getSentBy().matches(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.left_chat_view.setVisibility(View.GONE);
            holder.right_chat_view.setVisibility(View.VISIBLE);
            holder.right_chat_text.setText(message.getMessage());
            holder.right_chat_text_time.setText(new TimeFormatter(message.getSentTime()).formattedTime());
            holder.chat_read_status.setVisibility(message.isSeen() ? View.VISIBLE : View.GONE);
        } else {
            holder.right_chat_view.setVisibility(View.GONE);
            holder.left_chat_view.setVisibility(View.VISIBLE);
            holder.left_chat_text.setText(message.getMessage());
            holder.left_chat_text_time.setText(new TimeFormatter(message.getSentTime()).formattedTime());
        }

    }

    @Override
    public int getItemCount() {
        return allMessages.size();
    }

    public void setAllMessages(ArrayList<AllMessagesModel> allMessages, Context context) {
        this.allMessages = allMessages;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout left_chat_view, right_chat_view;
        TextView left_chat_text, right_chat_text;
        TextView right_chat_text_time, left_chat_text_time;
        ImageView chat_read_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding the views.
            left_chat_view = itemView.findViewById(R.id.left_chat_view);
            right_chat_view = itemView.findViewById(R.id.right_chat_view);
            left_chat_text = itemView.findViewById(R.id.left_chat_text);
            right_chat_text = itemView.findViewById(R.id.right_chat_text);
            chat_read_status = itemView.findViewById(R.id.chat_read_status);
            left_chat_text_time = itemView.findViewById(R.id.left_chat_text_time);
            right_chat_text_time = itemView.findViewById(R.id.right_chat_text_time);
        }
    }
}
