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

public class BookListFragment extends Fragment{

    private BookListAdapter mBookListAdapter;
    private static final String BOOK_LIST_ARGS = "arguments for book list";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView bookListView = (ListView) view.findViewById(R.id.book_list);



        // TODO  set up adapter class, then come back and try this:
        ArrayList<Book> bookList = getArguments().getParcelableArrayList(BOOK_LIST_ARGS);
        mBookListAdapter = new BookListAdapter(getActivity(), bookList);



        return view;
    }

    public static BookListFragment newInstance() {
        return new BookListFragment();
    }

}
