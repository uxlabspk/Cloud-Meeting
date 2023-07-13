package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.atomic.AtomicReference;

import io.github.uxlabspk.cloudmeeting.Fragments.ChatFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ClassesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.LecturesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ProfileFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.QuizFragment;
import io.github.uxlabspk.cloudmeeting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragments();

    }

    private void replaceFragments()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.edu_chat:
                    fragment = new ChatFragment();
                    break;
                case R.id.edu_classes:
                    fragment = new ClassesFragment();
                    break;
                case R.id.edu_lectures:
                    fragment = new LecturesFragment();
                    break;
                case R.id.edu_quizzes:
                    fragment = new QuizFragment();
                    break;
                case R.id.edu_profile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    return false;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        });

    }
}