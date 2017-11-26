package com.mark.androidfinal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 *
 */

public class ViewBookFragment extends Fragment {

    private Book passedBook;
    // DateFormatter for Date field.
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("M-dd-yyyy hh:mm:ss");

    ViewBookListener viewBookListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ViewBookListener) {
            this.viewBookListener = (ViewBookListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_book, container, false);

        TextView bookNameText = (TextView) view.findViewById(R.id.view_book_name_text);
        TextView readerText = (TextView) view.findViewById(R.id.view_reader_text);
        TextView pagesText = (TextView) view.findViewById(R.id.view_pages_read_text);
        TextView totalPagesText = (TextView) view.findViewById(R.id.view_total_pages_text);
        TextView dateStartText = (TextView) view.findViewById(R.id.view_date_start_text);
        Button updateButton = (Button) view.findViewById(R.id.view_update_button);
        Button cancelButton = (Button) view.findViewById(R.id.view_cancel_button);

        passedBook = getArguments().getParcelable(MainActivity.BOOK_KEY);

        bookNameText.setText(passedBook.getBook_name());
        readerText.setText(passedBook.getReader());
        pagesText.setText(passedBook.getPages_read() + "");
        totalPagesText.setText(passedBook.getTotal_pages() + "");
        dateStartText.setText(dateFormatter.format(passedBook.getStart_date()));


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(container.getContext());
                dialog.setContentView(R.layout.dialog_update);
                dialog.setTitle("Update Pages");

                final EditText editText = (EditText) dialog.findViewById(R.id.dialog_update_edit);
                Button updateButton = (Button) dialog.findViewById(R.id.dialog_update_button);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        String pagesTxt = editText.getText().toString();
                        if (!pagesTxt.equals("")) {
                            int pages = Integer.parseInt(pagesTxt);
                            bundle.putParcelable(MainActivity.BOOK_KEY, passedBook);
                            bundle.putInt(MainActivity.PAGES_KEY, pages);
                            viewBookListener.updatePages(bundle);
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    public interface ViewBookListener {
        void updatePages(Bundle bundle);
    }

    public static ViewBookFragment newInstance() {
        return new ViewBookFragment();
    }
}
