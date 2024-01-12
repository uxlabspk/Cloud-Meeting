package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AllLecturesAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AttendanceAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.Models.AllLecturesModel;
import io.github.uxlabspk.cloudmeeting.Models.AttendanceModel;
import io.github.uxlabspk.cloudmeeting.Models.AttendanceViewModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityAttendanceBinding;

public class Attendance extends AppCompatActivity {

    private ActivityAttendanceBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private String className;
    private String studentName;

    private String userRole;
    private long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // getting user role and section.
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userSection = pref.getString("User_class", null);
        userRole = pref.getString("User_role", null);
        String userSchool = pref.getString("User_School", null);

        // getting userName
        mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                studentName = user.getUserName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // ready the recycler view
        ArrayList<AttendanceViewModel> attendance = new ArrayList<>();
        AttendanceAdapter adapter = new AttendanceAdapter();
        binding.attendanceData.setAdapter(adapter);
        adapter.setAttendance(attendance);
        binding.attendanceData.setLayoutManager(new LinearLayoutManager(Attendance.this));

        mDatabase.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AllClassesModel classesModel = ds.getValue(AllClassesModel.class);
                    if (userSection.matches(classesModel.getSectionName())) {
                        className = classesModel.getClass_name();
                        mDatabase.getReference().child("Attendance").child(className).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                binding.notFound.setVisibility(View.VISIBLE);
                                if (snapshot.exists()) {
                                    binding.notFound.setVisibility(View.GONE);
                                    if (!attendance.isEmpty()) attendance.clear();
                                    AttendanceViewModel model = new AttendanceViewModel();
                                    model.setAttendanceCount(String.valueOf(snapshot.getChildrenCount()));
                                    model.setClassName(className);
                                    model.setStudentName(studentName);
                                    attendance.add(model);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}