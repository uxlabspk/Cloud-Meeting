package io.github.uxlabspk.cloudmeeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.uxlabspk.cloudmeeting.Fragments.ChatFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ClassesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.LecturesFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.ProfileFragment;
import io.github.uxlabspk.cloudmeeting.Fragments.QuizFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragments();

    }

    private void replaceFragments()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new ChatFragment());
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId())
                    {
                        case R.id.edu_chat:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new ChatFragment());
                            fragmentTransaction.commit();
                            break;

                        case R.id.edu_classes:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new ClassesFragment());
                            fragmentTransaction.commit();
                            break;

                        case R.id.edu_lectures:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new LecturesFragment());
                            fragmentTransaction.commit();
                            break;

                        case R.id.edu_quizzes:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new QuizFragment());
                            fragmentTransaction.commit();
                            break;

                        case R.id.edu_profile:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment());
                            fragmentTransaction.commit();
                            break;
                    }

                    return true;
                }
        );
    }
}