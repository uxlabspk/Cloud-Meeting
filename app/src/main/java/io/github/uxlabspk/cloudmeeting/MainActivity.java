package io.github.uxlabspk.cloudmeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

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

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragments();

    }

    private void replaceFragments()
    {
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();

        binding.navView.setOnItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.edu_chat:
                    transaction.replace(R.id.fragment_container, new ChatFragment());
//                    fragment = new ChatFragment();
                    break;
                case R.id.edu_classes:
                    transaction.replace(R.id.fragment_container, new ClassesFragment());
//                    fragment = new ClassesFragment();
                    break;
                case R.id.edu_lectures:
                    transaction.replace(R.id.fragment_container, new LecturesFragment());
//                    fragment = new LecturesFragment();
                    break;
                case R.id.edu_quizzes:
                    transaction.replace(R.id.fragment_container, new QuizFragment());
//                    fragment = new QuizFragment();
                    break;
                case R.id.edu_profile:
                    transaction.replace(R.id.fragment_container, new ProfileFragment());
//                    fragment = new ProfileFragment();
                    break;
                default:
                    return false;
            }
            transaction.commit();

            return true;
        });
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();

//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.edu_chat:
//                    fragment = new ChatFragment();
//                    break;
//                case R.id.edu_classes:
//                    fragment = new ClassesFragment();
//                    break;
//                case R.id.edu_lectures:
//                    fragment = new LecturesFragment();
//                    break;
//                case R.id.edu_quizzes:
//                    fragment = new QuizFragment();
//                    break;
//                case R.id.edu_profile:
//                    fragment = new ProfileFragment();
//                    break;
//                default:
//                    return false;
//            }
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//
//            return true;
//        });


    }
}