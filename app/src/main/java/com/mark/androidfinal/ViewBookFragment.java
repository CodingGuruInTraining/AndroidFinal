package com.mark.androidfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 */

public class ViewBookFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_book, container, false);

        TextView bookNameText = (TextView) view.findViewById(R.id.view_book_name_text);
        TextView readerText = (TextView) view.findViewById(R.id.view_reader_text);
        TextView pagesText = (TextView) view.findViewById(R.id.view_pages_read_text);
        TextView totalPagesText = (TextView) view.findViewById(R.id.view_total_pages_text);
        TextView dateStartText = (TextView) view.findViewById(R.id.view_date_start_text);
        Button updateButton = (Button) view.findViewById(R.id.view_update_button);
        Button cancelButton = (Button) view.findViewById(R.id.view_cancel_button);

        return view;
    }

    public static ViewBookFragment newInstance() {
        return new ViewBookFragment();
    }
}
