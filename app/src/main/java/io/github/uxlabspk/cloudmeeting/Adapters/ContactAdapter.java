package io.github.uxlabspk.cloudmeeting.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Users> allContacts = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_contact_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(allContacts.get(position).getUserName());
        holder.userEmail.setText(allContacts.get(position).getUserEmail());

        holder.contactContainer.setOnClickListener(view -> {
            AllChatUsersModel usersModel = new AllChatUsersModel(allContacts.get(position).getUserID(), allContacts.get(position).getUserName(), allContacts.get(position).getUserEmail(), allContacts.get(position).getUserImgUrl());
            usersModel.setLastSeen(new Date().getTime());
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Chat").child(allContacts.get(position).getUserID()).setValue(usersModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(view.getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                }
            });

            // other contact added

            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users currentUser = snapshot.getValue(Users.class);
                    AllChatUsersModel usersModel2 = new AllChatUsersModel(FirebaseAuth.getInstance().getCurrentUser().getUid(), currentUser.getUserName(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), currentUser.getUserImgUrl());
                    usersModel2.setLastSeen(new Date().getTime());
                    FirebaseDatabase.getInstance().getReference().child("Users").child(allContacts.get(position).getUserID()).child("Chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usersModel2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        });
    }

    @Override
    public int getItemCount() {
        return allContacts.size();
    }

    public void setContacts(ArrayList<Users> allContacts) {
        this.allContacts = allContacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView contactContainer;
        private TextView userName;
        private TextView userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactContainer = itemView.findViewById(R.id.contactsContainer);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
        }
    }
}
