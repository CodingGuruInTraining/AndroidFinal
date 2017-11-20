package com.mark.androidfinal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment Class
 */

public class NewBookFragment extends Fragment {

    private NewBookListener mNewBookListener;
    private String emptyField = "Please fill in all fields";
    private String parseError = "Error converting number";
    private String genError = "Unknown error occurred";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewBookListener) {
            mNewBookListener = (NewBookListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NewBookListener");
        }
    }

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
                try {
                    String name = nameEditText.getText().toString();
                    String reader = readerEditText.getText().toString();
                    String pagesStr = pagesEditText.getText().toString();

                    if (name.equals("") || reader.equals("") || pagesStr.equals("")) {
                        Toast.makeText(getActivity(), emptyField, Toast.LENGTH_SHORT).show();
                    } else {
                        int pages = Integer.parseInt(pagesStr);
                        mNewBookListener.newBookData(name, reader, pages);
                    }
                } catch (NumberFormatException error) {
                    Toast.makeText(getActivity(), parseError, Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    Toast.makeText(getActivity(), genError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }








    public interface NewBookListener {
        void newBookData(String name, String reader, int pages);
    }
}
