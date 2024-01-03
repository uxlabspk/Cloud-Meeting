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
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.BroadcastIntentHelper;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

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
        SharedPreferences pref = getSharedPreferences("User_role", Context.MODE_PRIVATE);
        String userRole = pref.getString("User_role", null);

        if (userRole.equals("Teacher") || userRole.equals("Admin")) {
            URL serverURL;
            try {
                serverURL = new URL("https://8x8.vc/vpaas-magic-cookie-be539426b2e04d128a81e57189d4d460/");// "https://meet.jit.si");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid server URL!");
            }
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    // When using JaaS, set the obtained JWT here
                    //.setToken("MyJWT")
                    // Different features flags can be set
//                     .setFeatureFlag("toolbox.enabled", false)
//                     .setFeatureFlag("filmstrip.enabled", false)
//                    .setToken("eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAvMTUzZjMyLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImlzcyI6ImNoYXQiLCJpYXQiOjE3MDM5MjUyMDYsImV4cCI6MTcwMzkzMjQwNiwibmJmIjoxNzAzOTI1MjAxLCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOmZhbHNlLCJvdXRib3VuZC1jYWxsIjp0cnVlLCJzaXAtb3V0Ym91bmQtY2FsbCI6ZmFsc2UsInRyYW5zY3JpcHRpb24iOnRydWUsInJlY29yZGluZyI6dHJ1ZX0sInVzZXIiOnsiaGlkZGVuLWZyb20tcmVjb3JkZXIiOmZhbHNlLCJtb2RlcmF0b3IiOnRydWUsIm5hbWUiOiJtdWhhbW1hZG5hdmVlZCIsImlkIjoiYXV0aDB8NjRiMTAwYWQ0NWRlMzE1ZTFmODM1MmQ1IiwiYXZhdGFyIjoiIiwiZW1haWwiOiJtdWhhbW1hZG5hdmVlZGNpc0BnbWFpbC5jb20ifX0sInJvb20iOiIqIn0.WPNEyNAfQqcOYi4q4JLs4tx0ho5OrUhT2PHJ0w_Mzhar2GSGYI6l1s5gYcyFRf_vnZRaIdX1qyFjRfrbm-cN2c0Ly9LlFjw7Khcjj-pqRb20vAHB1rIn1YnBNpw-soVNI57WYbNAF2RVvfovEi2Irc6oerWMwQGF9ZdZiY1LD_5fvqOeH6DBc2_dRag3ECn3WoA1llYWr3r3uJPNFor9MC4zSmExY2yIDeUU7XF5Zbc2tOmnxkOkvoamPG2_m7C0QEsdkREGz1dMYF2vaVbWjih4Yr7ynGkXkLiruzfoleGv4eXUMVtDjtsbkd3GE89Wwzy3eSDzfzTt75hpoNKhKw")
                    .setFeatureFlag("welcomepage.enabled", false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

            registerForBroadcastMessages();

        } else {
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
                    .setToken("eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAvMTUzZjMyLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImlzcyI6ImNoYXQiLCJpYXQiOjE3MDM5MjUyMDYsImV4cCI6MTcwMzkzMjQwNiwibmJmIjoxNzAzOTI1MjAxLCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtYmU1Mzk0MjZiMmUwNGQxMjhhODFlNTcxODlkNGQ0NjAiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOmZhbHNlLCJvdXRib3VuZC1jYWxsIjp0cnVlLCJzaXAtb3V0Ym91bmQtY2FsbCI6ZmFsc2UsInRyYW5zY3JpcHRpb24iOnRydWUsInJlY29yZGluZyI6dHJ1ZX0sInVzZXIiOnsiaGlkZGVuLWZyb20tcmVjb3JkZXIiOmZhbHNlLCJtb2RlcmF0b3IiOnRydWUsIm5hbWUiOiJtdWhhbW1hZG5hdmVlZCIsImlkIjoiYXV0aDB8NjRiMTAwYWQ0NWRlMzE1ZTFmODM1MmQ1IiwiYXZhdGFyIjoiIiwiZW1haWwiOiJtdWhhbW1hZG5hdmVlZGNpc0BnbWFpbC5jb20ifX0sInJvb20iOiIqIn0.WPNEyNAfQqcOYi4q4JLs4tx0ho5OrUhT2PHJ0w_Mzhar2GSGYI6l1s5gYcyFRf_vnZRaIdX1qyFjRfrbm-cN2c0Ly9LlFjw7Khcjj-pqRb20vAHB1rIn1YnBNpw-soVNI57WYbNAF2RVvfovEi2Irc6oerWMwQGF9ZdZiY1LD_5fvqOeH6DBc2_dRag3ECn3WoA1llYWr3r3uJPNFor9MC4zSmExY2yIDeUU7XF5Zbc2tOmnxkOkvoamPG2_m7C0QEsdkREGz1dMYF2vaVbWjih4Yr7ynGkXkLiruzfoleGv4eXUMVtDjtsbkd3GE89Wwzy3eSDzfzTt75hpoNKhKw")
                    // When using JaaS, set the obtained JWT here
                    //.setToken("MyJWT")
                    // Different features flags can be set
                    // .setFeatureFlag("toolbox.enabled", false)
                    // .setFeatureFlag("filmstrip.enabled", false)
                    .setFeatureFlag("welcomepage.enabled", false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

            registerForBroadcastMessages();
        }

        // join class
        binding.joinClass.setOnClickListener(view -> {
            JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
            userInfo.setDisplayName(userName);

            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(RoomName)
                    .setUserInfo(userInfo)
                    // Settings for audio and video
                    .setAudioMuted(binding.audioCheck.isChecked())
                    .setVideoMuted(binding.vdoCheck.isChecked())
                    .build();
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(this, options);
        });
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

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.getAction());
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());
                ... other events
         */
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