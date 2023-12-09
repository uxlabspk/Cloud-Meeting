package io.github.uxlabspk.cloudmeeting.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.uxlabspk.cloudmeeting.Adapters.AllChatUsersAdapter;
import io.github.uxlabspk.cloudmeeting.Adapters.AllClassesAdapter;
import io.github.uxlabspk.cloudmeeting.Classes.ProgressStatus;
import io.github.uxlabspk.cloudmeeting.ContactList;
import io.github.uxlabspk.cloudmeeting.JoinClass;
import io.github.uxlabspk.cloudmeeting.Models.AllChatUsersModel;
import io.github.uxlabspk.cloudmeeting.Models.AllClassesModel;
import io.github.uxlabspk.cloudmeeting.R;
import io.github.uxlabspk.cloudmeeting.add_classes_activity;
import io.github.uxlabspk.cloudmeeting.databinding.FragmentClassesBinding;

public class ClassesFragment extends Fragment {

    FragmentClassesBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    public ClassesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        // ready the firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // on add class
        binding.addClasses.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), add_classes_activity.class));
        });

        // getting list of users.
        ArrayList<AllChatUsersModel> allChatUsers = new ArrayList<>();
        AllChatUsersAdapter adapter = new AllChatUsersAdapter();
        adapter.setAllChatUsers(allChatUsers, getContext());
        binding.rvClasses.setLayoutManager(new LinearLayoutManager(getContext()));
        getClasses();


//        // Refresh the classes
//        binding.refreshClasses.setOnClickListener(view -> refreshClasses());
//
//        // determining the user role.
//        SharedPreferences pref = getActivity().getSharedPreferences("User_role", Context.MODE_PRIVATE);
//        String userRole = pref.getString("User_role", null);
//
//        Toast.makeText(getContext(), userRole, Toast.LENGTH_SHORT).show();
//
//        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
//            binding.addClasses.setVisibility(View.VISIBLE);
//
//            binding.addClasses.setOnClickListener(view -> {
//                startActivity(new Intent(getContext(), add_classes_activity.class));
//            });
//
//        } else {
//            binding.addClasses.setVisibility(View.GONE);
//        }
//
//        // all classes
//        ArrayList<AllClassesModel> allClasses = new ArrayList<>();
//
//        allClasses.add(new AllClassesModel("Mathematics", "Mon, Tue, Wed, Thr, Fri, Sat", "12:00AM"));
//        allClasses.add(new AllClassesModel("Physics", "Mon, Tue, Wed, Thr, Fri, Sat", "01:00PM"));
//
//        AllClassesAdapter adapter = new AllClassesAdapter();
//        adapter.setAllClasses(allClasses, getContext());
//
//        binding.rvClasses.setAdapter(adapter);
//        binding.rvClasses.setLayoutManager(new LinearLayoutManager(getContext()));
//
//



//        // test class module
//        binding.addClasses.setOnClickListener(view -> {
//            // Somewhere early in your app.
//            JitsiMeetConferenceOptions defaultOptions
//                    = null;
//            try {
//                defaultOptions = new JitsiMeetConferenceOptions.Builder()
//                .setServerURL(new URL("https://8x8.vc"))
//                .setFeatureFlag("welcomepage.enabled", false)
//                 .setConfigOverride("requireDisplayName", false)
//                .build();
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            }
//            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
//
//            JitsiMeetConferenceOptions options
//                    = new JitsiMeetConferenceOptions.Builder()
//                    .setToken("eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAvMTUzZjMyLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImlzcyI6ImNoYXQiLCJpYXQiOjE3MDE0MTE4OTcsImV4cCI6MTcwMTQxOTA5NywibmJmIjoxNzAxNDExODkyLCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOnRydWUsIm91dGJvdW5kLWNhbGwiOnRydWUsInNpcC1vdXRib3VuZC1jYWxsIjpmYWxzZSwidHJhbnNjcmlwdGlvbiI6dHJ1ZSwicmVjb3JkaW5nIjp0cnVlfSwidXNlciI6eyJoaWRkZW4tZnJvbS1yZWNvcmRlciI6ZmFsc2UsIm1vZGVyYXRvciI6dHJ1ZSwibmFtZSI6IiIsImlkIjoiYXV0aDB8NjRiMTAwYWQ0NWRlMzE1ZTFmODM1MmQ1IiwiYXZhdGFyIjoiIiwiZW1haWwiOiIifX0sInJvb20iOiIqIn0.X_Zl8OdsxH4VCqMyUtrDxUq6Zrl-jRjq2mxBccJ4mefFEqr765S9WytihInWc3otx4bJYUOAM6vIbeHM11BdE2VQGqbJz0R8aLwI9SENyjNBExQ0VBr05fJ7J1J4EbEPSXSerqo_ejDkJ08vDzruq7_3o0sD0_INbS6s4BUdd6idm0uAvtDPLSdw0ijqpVtMkF8-08IApMph8yZ8hQUopiOWhWRfATxJs5qj1GaH54CKtVjxp3YxlKMtakspiR2ggseAIFllbCqr7ANmJLUWzh7IiWNEXetfbdSwVSM8nlMkyBuQDiMvAVX2sEQCfjpDrzr-crRZ1MVVOopGSfZxww")
//                    .setRoom("vpaas-magic-cookie-be539426b2e04d128a81e57189d4d460" + "/" + "RoomPHYSICES")
//
//                    // Settings for audio and video
//                    //.setAudioMuted(true)
//                    //.setVideoMuted(true)
//                    .build();
//
//            JitsiMeetActivity.launch(getContext(), options);
//        });
//
//
//
//


    }

    private void getClasses() {
        mDatabase.getReference().child(mAuth.getCurrentUser().getUid()).child("Class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.notFound.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                        Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );
//                        HashMap<String, String> data = (HashMap<String, String>) dataSnapshot.getValue(HashMap.class);
//                        allChatUsers.add(data);
                    }
//                    binding.rvClasses.setAdapter();
//                    adapter.notifyDataSetChanged();
                } else {
                    binding.notFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}