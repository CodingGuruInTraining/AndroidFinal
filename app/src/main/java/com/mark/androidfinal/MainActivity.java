package com.mark.androidfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NewBookFragment.NewBookListener {

    protected static final String NAV_KEY = "navigation menu";
    protected static final String ALL_BOOKS_KEY = "all_books";

    private static final String TAG = "debugger extraordinaire";

    private DatabaseReference mDatabaseReference;

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
                // TODO attach query results to fragment
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
                fm.beginTransaction().replace(R.id.main_container, fragment);
//                fm.beginTransaction().add(R.menu.navigation, NAV_KEY);
                fm.beginTransaction().commit();
                return true;
            } else {
                return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO do you need a FragmentPagerAdapter???

        // Sets up nav bar.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // TODO try following link to keep nav bar visible with other fragments:
        // http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/

        // TODO setup firebase database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference();
        mDatabaseReference = dbReference.child(ALL_BOOKS_KEY);
    }

    // Returning function/call from NewBookFragment.
    @Override
    public void newBookData(String name, String reader, int pages) {
        // Creates new Book object.
        Book newBook = new Book(name, reader, pages);
        // New Book object is added to Firebase.
        saveNewBook(newBook);
        // TODO update ArrayAdapter if using one for full list of books
        // TODO replace fragment to something else
    }

    private void saveNewBook(Book newBook) {
        // Saving Book to database.
        // Creates a new child reference of global DatabaseReference.
        DatabaseReference newReference = mDatabaseReference.push();

        // Set the value of new child reference to the passed Book object.
        newReference.setValue(newBook);
    }

    private void queryAllBooks() {
        // Queries the database for all entries.

        mDatabaseReference.orderByChild("book_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ALL DB ENTRIES: " + dataSnapshot.toString());

                ArrayList<Book> allBooks = new ArrayList<Book>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Book book = childSnapshot.getValue(Book.class);
                    allBooks.add(book);
                }

                // TODO Deliver allBooks somewhere for processing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Firebase Error fetching all entries", databaseError.toException());
            }
        });
    }

    private void searchForBooks(String bookName) {
        mDatabaseReference.orderByChild("book_name").equalTo(bookName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Book> matches = new ArrayList<Book>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Book book = childSnapshot.getValue(Book.class);
                    matches.add(book);
                }

                // TODO Deliver ArrayList of results for processing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Firebase Error searching entries", databaseError.toException());
            }
        });
    }
}
