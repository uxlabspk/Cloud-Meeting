package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.uxlabspk.cloudmeeting.Adapters.AllAssesmentAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AssesmentResultAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AssesmentResultViewAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AttendanceAdapter;
import io.github.uxlabspk.cloudmeeting.Models.AllAssesmentsModel;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.Models.AssesmentDetailsModel;
import io.github.uxlabspk.cloudmeeting.Models.AttendanceViewModel;
import io.github.uxlabspk.cloudmeeting.Models.SubmissionModel;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityQuizResultsBinding;

public class QuizResults extends AppCompatActivity {

    private ActivityQuizResultsBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // getting user classes
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userSection = pref.getString("User_class", null);
        String userSchool = pref.getString("User_School", null);

        ArrayList<AssesmentDetailsModel> allAssesments = new ArrayList<>();
        AssesmentResultViewAdapter adapter = new AssesmentResultViewAdapter();
        binding.rvResults.setAdapter(adapter);
        binding.rvResults.setLayoutManager(new LinearLayoutManager(QuizResults.this));
        mDatabase.getReference().child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!allAssesments.isEmpty()) allAssesments.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AllClassesModel classesModel = ds.getValue(AllClassesModel.class);

                    if (userSection.matches(classesModel.getSectionName()) && userSchool.matches(classesModel.getSchoolName())) {
                        mDatabase.getReference().child("Assesments").child(classesModel.getClass_name()).child("creation").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    if (!allAssesments.isEmpty()) allAssesments.clear();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        AssesmentDetailsModel model = dataSnapshot.getValue(AssesmentDetailsModel.class);
                                        allAssesments.add(model);
                                    }
                                    adapter.setAllAssesments(allAssesments);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
//                        AssesmentDetailsModel model = ds.getValue(AssesmentDetailsModel.class);
//                        allAssesments.add(model);
                    }
                }
                adapter.setAllAssesments(allAssesments);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        prototype = (CardView) findViewById(R.id.prototype_quiz1_result_box);
//        // click listner for quiz/assignments details.
//        prototype.setOnClickListener(view -> startActivity(new Intent(this, AssesmentDetails.class)));
    }
}