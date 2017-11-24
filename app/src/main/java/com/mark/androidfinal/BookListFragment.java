package com.mark.androidfinal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *
 */

public class BookListFragment extends Fragment {

    private BookListAdapter mBookListAdapter;
    private static final String BOOK_LIST_ARGS = "arguments for book list";
    protected static ArrayList<Book> allBooksList;

//    private RequestQueryListener mRequestQueryListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView bookListView = (ListView) view.findViewById(R.id.book_list);



        // TODO  set up adapter class, then come back and try this:
        // TODO if there is time, try making Book Class parcelable and then send in bundle
//        ArrayList<Book> bookList = getArguments().getParcelableArrayList(BOOK_LIST_ARGS);

        allBooksList = getArguments().getParcelableArrayList(MainActivity.ALL_BOOKS_KEY);

//        mRequestQueryListener.requestAllQuery(true);


        mBookListAdapter = new BookListAdapter(getActivity(), allBooksList);
        bookListView.setAdapter(mBookListAdapter);


        return view;
    }


//    @Override
//    public void allQueryResults(ArrayList<Book> allBooks) {
//        allBooksList = allBooks;
//    }
//
//    public interface RequestQueryListener {
//        void requestAllQuery(boolean yesPlease);
//    }

    public static BookListFragment newInstance() {
        return new BookListFragment();
    }

    protected void setAllBooksList(ArrayList<Book> books) {
        allBooksList = books;
    }

}
