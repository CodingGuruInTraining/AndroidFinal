package com.mark.androidfinal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 *
 */

public class BookListFragment extends Fragment{

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView bookList = (ListView) view.findViewById(R.id.book_list);


        return view;
    }

    public static BookListFragment newInstance() {
        return new BookListFragment();
    }

}
