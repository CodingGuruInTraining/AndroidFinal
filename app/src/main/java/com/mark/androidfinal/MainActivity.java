package com.mark.androidfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NewBookFragment.NewBookListener {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Create null Fragment outside of switch. Its content will be decided
            // in the switch.
            Fragment fragment = new Fragment();
            FragmentManager fm = getSupportFragmentManager();

            // Switch statement to determine which nav bar button was pressed.
            // TODO refactor names and text once decided on
            switch (item.getItemId()) {
                case R.id.navigation_home:      // Maybe view all books in database?
                    fragment = BookListFragment.newInstance();
                    break;
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
                case R.id.navigation_dashboard: // Maybe add new book?
                    fragment = NewBookFragment.newInstance();
                    break;
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
                case R.id.navigation_notifications: // Maybe update existing book?

                    break;
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            // Checks if the fragment was set to anything (a button was pressed).
            if (fragment != null) {
                // Replaces current fragment with indicated one.
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

        // Sets up nav bar.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // Returning function/call from NewBookFragment.
    @Override
    public void newBookData(String name, String reader, int pages) {
        // Creates new Book object.
        Book newBook = new Book(name, reader, pages);
        // TODO add Book to list or database somewhere
        // TODO update ArrayAdapter if using one for full list of books
        // TODO replace fragment to something else
    }
}
