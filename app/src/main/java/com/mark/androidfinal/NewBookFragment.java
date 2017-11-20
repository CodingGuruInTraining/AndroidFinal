package com.mark.androidfinal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment Class
 */

public class NewBookFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_book, container, false);

        final EditText nameEditText = (EditText) view.findViewById(R.id.new_book_name);
        final EditText readerEditText = (EditText) view.findViewById(R.id.new_book_reader);
        final EditText pagesEditText = (EditText) view.findViewById(R.id.new_book_pages);
        Button submitButton = (Button) view.findViewById(R.id.new_book_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String reader = readerEditText.getText().toString();
                int pages = Integer.parseInt(pagesEditText.getText().toString());
            }
        });

        return view;
    }
}
