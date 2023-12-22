package com.bubnov.lab5;

import static com.bubnov.lab5.MainActivity.provider;

import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bubnov.lab5.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class FourthFragment extends Fragment {
    private TextInputEditText id;
    private Button buttonSave;

    public FourthFragment(){
        super(R.layout.fragment_fourth);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = view.findViewById(R.id.idtext);
        buttonSave = view.findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = id.getText().toString();
                long iid = 0;
                if(!sid.isEmpty()) {
                    try {
                        iid = Integer.parseInt(sid);
                    } catch (final NumberFormatException e) {
                        Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String[] projection = {
                            DatabaseDescription.Student._ID
                    };
                    String selection = DatabaseDescription.Student._ID + "=?";
                    String[] selectionArgs = {sid};

                    Cursor cursor = provider.query(DatabaseDescription.Student.CONTENT_URI, projection,
                            selection,selectionArgs,null);
                    try {
                        if (cursor.getCount() > 0) {
                            int xint = provider.delete(DatabaseDescription.Student.buildContactUri(iid),null,null);
                            Toast.makeText(getActivity().getApplicationContext(), R.string.toastSuccess, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                        }
                    } finally {
                        cursor.close();
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}