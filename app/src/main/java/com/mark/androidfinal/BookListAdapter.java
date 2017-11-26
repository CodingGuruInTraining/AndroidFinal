package com.mark.androidfinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 */

public class BookListAdapter extends ArrayAdapter<Book> {

    private Activity mActivity;

    public BookListAdapter(Activity context, ArrayList<Book> bookList) {
        super(context, R.layout.book_item, bookList);
        this.mActivity = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.book_item, parent, false);
        }

        // Locates the Book object from the adapter.
        Book book = getItem(position);

        // Sets up widgets.
        TextView readerTextView = (TextView) view.findViewById(R.id.list_reader);
        TextView bookNameTextView = (TextView) view.findViewById(R.id.list_book_name);
        TextView pagesTextView = (TextView) view.findViewById(R.id.list_pages_read);


        readerTextView.setText(book.getReader());
        bookNameTextView.setText(book.getBook_name());
        pagesTextView.setText(String.valueOf(book.getPages_read()));


        return view;
    }
}
