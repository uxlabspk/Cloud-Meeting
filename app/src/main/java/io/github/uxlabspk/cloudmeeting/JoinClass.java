package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.ServerValues;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.BroadcastIntentHelper;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.uxlabspk.cloudmeeting.Models.AttendanceModel;
import io.github.uxlabspk.cloudmeeting.Models.Users;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityJoinClassBinding;
import timber.log.Timber;

public class JoinClass extends AppCompatActivity {

    ActivityJoinClassBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    String userName;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        // Ready Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // goBack
        binding.goBack.setOnClickListener(view -> onBackPressed());

        // Room name
        String RoomName = getIntent().getStringExtra("RoomName");

        // get User Name
        mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userDetails = snapshot.getValue(Users.class);
                userName = userDetails.getUserName();
                userRole = userDetails.getUserRole();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JoinClass.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // determining the user role.
//        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
//        String userRole = pref.getString("User_role", null);

        URL serverURL;
        try {
            serverURL = new URL("https://8x8.vc/vpaas-magic-cookie-be539426b2e04d128a81e57189d4d460/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setToken("eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAvMTUzZjMyLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImlzcyI6ImNoYXQiLCJpYXQiOjE3MDQ3ODY1NzAsImV4cCI6MTcwNDc5Mzc3MCwibmJmIjoxNzA0Nzg2NTY1LCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOnRydWUsIm91dGJvdW5kLWNhbGwiOnRydWUsInNpcC1vdXRib3VuZC1jYWxsIjpmYWxzZSwidHJhbnNjcmlwdGlvbiI6dHJ1ZSwicmVjb3JkaW5nIjp0cnVlfSwidXNlciI6eyJoaWRkZW4tZnJvbS1yZWNvcmRlciI6ZmFsc2UsIm1vZGVyYXRvciI6dHJ1ZSwibmFtZSI6IiIsImlkIjoiYXV0aDB8NjRiMTAwYWQ0NWRlMzE1ZTFmODM1MmQ1IiwiYXZhdGFyIjoiIiwiZW1haWwiOiJtdWhhbW1hZG5hdmVlZGNpc0BnbWFpbC5jb20ifX0sInJvb20iOiIqIn0.Xw4Duw39vyFhBiIpHliu7KNPoHAXRB8RssEF7txfllQWsVLtDKvRDLqLL8_Rh_2K1WeSiH0Hx3CNMPi7QkuYgpwWn1PiR6B4tmR346eAlij0vQNbaTKudrlCX0OFlcj0xaPhV2_P67HtBtI5F7kE0ktm6lzDGC9qWeVUurXry61WS_h63Ui01qFfLA6lfy0xi9jkrDYD2FM2Bg8V7xbZWpvj36UgTTyDz80epEA7sjQVtu9zaZJ2_xdwDm5dqAeTPpjuj8aHWyDVRzIKTOUtxSdREp97_SfzD7XUrHNCkXD5ivk7_aOnyyrfdtw4LsLW2AedYUj0G2IGDQ9GoKOHmQ")
                .setFeatureFlag("welcomepage.enabled", false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        registerForBroadcastMessages();

        // join class
        binding.joinClass.setOnClickListener(view -> {
            JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
            userInfo.setDisplayName(userName);

            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(RoomName)
                    .setUserInfo(userInfo)
                    // Settings for audio and video
                    .setAudioMuted(binding.audioCheck.isChecked())
                    .setVideoMuted(binding.vdoCheck.isChecked())
                    .build();

            JitsiMeetActivity.launch(this, options);
            markAttendance();
        });
    }

    private void markAttendance() {
        AttendanceModel model = new AttendanceModel(ServerValue.TIMESTAMP);
        mDatabase.getReference().child("Attendance").child(getIntent().getStringExtra("RoomName")).child(mAuth.getCurrentUser().getUid()).push().setValue(model);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onBroadcastReceived(intent);
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        super.onDestroy();
    }

    private void registerForBroadcastMessages() {
        IntentFilter intentFilter = new IntentFilter();

        for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.getAction());
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    // Example for handling different JitsiMeetSDK events
    private void onBroadcastReceived(Intent intent) {
        if (intent != null) {
            BroadcastEvent event = new BroadcastEvent(intent);

            switch (event.getType()) {
                case CONFERENCE_JOINED:
                    Timber.i("Conference Joined with url%s", event.getData().get("url"));
                    break;
                case PARTICIPANT_JOINED:
                    Timber.i("Participant joined%s", event.getData().get("name"));
                    break;
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private void hangUp() {
        Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(hangupBroadcastIntent);
    }
}