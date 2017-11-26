package com.mark.androidfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewBookFragment.NewBookListener,
        BookListFragment.ClickedBookListener, ViewBookFragment.ViewBookListener {

    protected static final String NAV_KEY = "navigation menu";
    protected static final String ALL_BOOKS_KEY = "all_books";
    protected static final String BOOK_KEY = "one_book_to_rule_them_all";
    protected static final String PAGES_KEY = "updating_pages";

    private static final String TAG = "debugger extraordinaire";

    private DatabaseReference mDatabaseReference;
    private ArrayList<Book> mBookArrayList;
    Query allQuery;
    ValueEventListener queryListener;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

//    private SendQueryListener mSendQueryListener;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Create null Fragment outside of switch. Its content will be decided
            // in the switch.
            Fragment fragment = new Fragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();

            // Switch statement to determine which nav bar button was pressed.
            // TODO refactor names and text once decided on
            switch (item.getItemId()) {
                case R.id.navigation_home:      // Maybe view all books in database?
                    fragment = BookListFragment.newInstance();
//                    queryAllBooks();
                    bundle.putParcelableArrayList(ALL_BOOKS_KEY, mBookArrayList);
                    break;
                case R.id.navigation_dashboard: // Maybe add new book?
                    fragment = NewBookFragment.newInstance();
                    break;
                case R.id.navigation_notifications: // Maybe update existing book?

                    break;
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
                default:
//                    fragment = BookListFragment.newInstance();
                    break;
            }
            // Checks if the fragment was set to anything (a button was pressed).
            if (fragment != null) {
                fragment.setArguments(bundle);
                // Replaces current fragment with indicated one.
                ft.replace(R.id.main_container, fragment).commit();
                return true;
            }
            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO do you need a FragmentPagerAdapter???

//        buildFragmentList();

        // Sets up nav bar.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference();
        mDatabaseReference = dbReference.child(ALL_BOOKS_KEY);
        allQuery = mDatabaseReference.orderByChild("book_name");
        queryAllBooks();

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.main_container, BookListFragment.newInstance());
//        ft.commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.main_container, BookListFragment.newInstance()).commit();


//        switchFragments(0);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.main_container, BookListFragment.newInstance());
//        ft.commit();
//    }

    // Returning function/call from NewBookFragment.
    @Override
    public void newBookData(String name, String reader, int pages) {
        // Creates new Book object.
        Book newBook = new Book(name, reader, pages);
        // New Book object is added to Firebase.
        saveNewBook(newBook);
        // TODO maybe load a "home" fragment instead
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, BookListFragment.newInstance()).commit();
        // TODO update ArrayAdapter if using one for full list of books
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
// TODO need Query object???
        mDatabaseReference.orderByChild("book_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ALL DB ENTRIES: " + dataSnapshot.toString());

                ArrayList<Book> allBooks = new ArrayList<Book>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Book book = childSnapshot.getValue(Book.class);
//                    Book book = (Book) childSnapshot.getValue();
                    allBooks.add(book);
                }
                mBookArrayList = allBooks;
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

    @Override
    public void openClickedBook(Bundle bundle) {
//        Book clickedBook = bundle.getParcelable(BOOK_KEY);

//        switchFragments(2);

        ViewBookFragment fragment = ViewBookFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        ft.commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void updatePages(Bundle bundle) {
        Book book = bundle.getParcelable(BOOK_KEY);
        int pages = bundle.getInt(PAGES_KEY);

        try {
            long startDate = book.getStart_date().getTime();
//            mDatabaseReference.child("start_date").child("time").equalTo(startDate).set



        } catch (NullPointerException er) {
            er.fillInStackTrace();
            Toast.makeText(MainActivity.this, "Can't locate book", Toast.LENGTH_SHORT).show();
        }
    }


//    private void switchFragments(int position) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_container, fragmentList.get(position))
//                .commit();
//    }
//
//    private void buildFragmentList() {
//        BookListFragment bookListFragment = BookListFragment.newInstance();
//        NewBookFragment newBookFragment = NewBookFragment.newInstance();
//        ViewBookFragment viewBookFragment = ViewBookFragment.newInstance();
//
//        fragmentList.add(bookListFragment);
//        fragmentList.add(newBookFragment);
//        fragmentList.add(viewBookFragment);
//    }

}




// References:
// parcing class - http://www.parcelabler.com/
// column spacing - https://stackoverflow.com/questions/1666685/android-stretch-columns-evenly-in-a-tablelayout
// fragment managing strategy - http://blog.iamsuleiman.com/using-bottom-navigation-view-android-design-support-library/

