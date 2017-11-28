package com.mark.androidfinal;

import android.graphics.Paint;
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
import android.view.View;
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
import java.util.Collections;
import java.util.Comparator;
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

    private boolean mainActive;
    private TextView mainTextView;

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
//                case R.id.navigation_notifications: // Maybe update existing book?
//
//                    break;
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

                loadMainPage();
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

        mainActive = true;
        mainTextView = (TextView) findViewById(R.id.main_welcome);

        // Sets up nav bar.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference();
        mDatabaseReference = dbReference.child(ALL_BOOKS_KEY);
        allQuery = mDatabaseReference.orderByChild("userId");

// TODO need to give firebase permissions:
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
        Toast.makeText(MainActivity.this, "Book added", Toast.LENGTH_SHORT).show();
        loadMainPage();
//        loadBookList();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        BookListFragment fragment = BookListFragment.newInstance();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(ALL_BOOKS_KEY, mBookArrayList);
//        ft.replace(R.id.main_container, fragment).commit();
    }

    private void saveNewBook(Book newBook) {
        // Saving Book to database.
        // Creates a new child reference of global DatabaseReference.
        DatabaseReference newReference = mDatabaseReference.push();

        String uniqueId = newReference.getKey();
        newBook.setUniqueId(uniqueId);

        String userId = getSharedPreferences(SignInActivity.USERS_PREFS, MODE_PRIVATE).getString(SignInActivity.FIREBASE_USER_ID_PREF_KEY, "something is wrong");
        newBook.setUserId(userId);

        // Set the value of new child reference to the passed Book object.
        newReference.setValue(newBook, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Log.d(TAG, "entry added to database");
                } else {
                    Log.e(TAG, "failed adding to database");
                }
            }
        });
        mBookArrayList.add(newBook);
//        queryAllBooks();
    }

    private void queryAllBooks() {

//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference dbReference = db.getReference();
//        mDatabaseReference = dbReference.child(ALL_BOOKS_KEY);
//        allQuery = mDatabaseReference.orderByChild("userId");

//        String userId = getSharedPreferences(SignInActivity.USERS_PREFS, MODE_PRIVATE).getString(SignInActivity.FIREBASE_USER_ID_PREF_KEY, "dunno what im doing");
////        Query query = dbReference.orderByChild("userId").equalTo(userId);
//        allQuery = mDatabaseReference.equalTo(userId);
//
//        allQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Book> allBooks = new ArrayList<Book>();
//                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                    Book book = (childSnapshot.getValue(Book.class));
//                    book.setFirebaseKey(childSnapshot.getKey());
//                    allBooks.add(book);
//                }
//                mBookArrayList = allBooks;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG, "Firebase Error fetching all entries", databaseError.toException());
//            }
//        });


        // Queries the database for all entries.
        mDatabaseReference.orderByChild("pages_read").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ALL DB ENTRIES: " + dataSnapshot.toString());

                ArrayList<Book> allBooks = new ArrayList<Book>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Book book = childSnapshot.getValue(Book.class);
//                    Book book = (Book) childSnapshot.getValue();
                    book.setFirebaseKey(childSnapshot.getKey());
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
        String bookId = book.getUniqueId();

        try {
//            long startDate = book.getStart_date().getTime();
//            mDatabaseReference.child("start_date").child("time").equalTo(startDate).set
            int prevPages = book.getPages_read();
            int newTotal = prevPages + pages;
            if (newTotal <= book.getTotal_pages()) {
                mDatabaseReference.child(bookId).child("pages_read").setValue(pages + prevPages);
                Toast.makeText(MainActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                for (Book x : mBookArrayList) {
                    if (x.getUniqueId().equals(bookId)) {
                        x.setPages_read(newTotal);
                        break;
                    }
                }
//            loadBookList();
                loadMainPage();
            } else {
                Toast.makeText(MainActivity.this, "Can't read more than the total", Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException er) {
            er.fillInStackTrace();
            Toast.makeText(MainActivity.this, "Can't locate book", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadBookList() {
        queryAllBooks();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BookListFragment fragment = BookListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ALL_BOOKS_KEY, mBookArrayList);
        ft.replace(R.id.main_container, fragment).commit();
    }


    private void loadMainPage() {
        if (mainActive) {
            mainTextView.setVisibility(View.GONE);
            mainActive = false;
        } else {
            mainTextView.setVisibility(View.VISIBLE);
            mainActive = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
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
// simple update statement - https://stackoverflow.com/questions/33315353/update-specific-keys-using-firebase-for-android
// getting past firebase permissions - https://stackoverflow.com/questions/37477644/firebase-permission-denied-error
// reverse order of arraylist - https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property

