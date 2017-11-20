package com.mark.androidfinal;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 *
 */

public class BookListAdapter extends ArrayAdapter<Book> {


    public BookListAdapter(Activity context, ArrayList<Book> bookList) {
        super(context, R.layout.book_item, bookList);
    }
}
