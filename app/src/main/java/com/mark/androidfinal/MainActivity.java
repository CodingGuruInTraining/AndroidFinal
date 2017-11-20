package com.mark.androidfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            Fragment fragment = new Fragment();
            FragmentManager fm = getSupportFragmentManager();


            switch (item.getItemId()) {
                case R.id.navigation_home:      // Maybe view all books in database?
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
                case R.id.navigation_dashboard: // Maybe add new book?
                    fragment = new NewBookFragment();
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
                case R.id.navigation_notifications: // Maybe update existing book?
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            if (fragment != null) {
                fm.beginTransaction().replace(R.id.main_container, fragment).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
