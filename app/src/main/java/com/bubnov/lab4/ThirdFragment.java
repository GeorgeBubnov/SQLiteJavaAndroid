package com.bubnov.lab4;

import static com.bubnov.lab4.MainActivity.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bubnov.lab4.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class ThirdFragment extends Fragment {
    private TextInputEditText id;
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText average;
    private Button buttonSave;

    public ThirdFragment(){
        super(R.layout.fragment_third);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = view.findViewById(R.id.idtext);
        lastName = view.findViewById(R.id.lastName);
        firstName = view.findViewById(R.id.firstName);
        middleName = view.findViewById(R.id.middleName);
        average = view.findViewById(R.id.average);
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

                    String lastNameText = lastName.getText().toString();
                    String firstNameText = firstName.getText().toString();
                    String middleNameText = middleName.getText().toString();
                    String averageText = average.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put(DatabaseDescription.Student.COLUMN_LASTNAME, lastNameText);
                    values.put(DatabaseDescription.Student.COLUMN_FIRSTNAME, firstNameText);
                    values.put(DatabaseDescription.Student.COLUMN_MIDDLENAME, middleNameText);
                    values.put(DatabaseDescription.Student.COLUMN_AVERAGE, averageText);

                    String[] projection = {
                            DatabaseDescription.Student._ID
                    };
                    String selection = DatabaseDescription.Student._ID + "=?";
                    String[] selectionArgs = {sid};

                    Cursor cursor = provider.query(DatabaseDescription.Student.CONTENT_URI, projection,
                            selection,selectionArgs,null);
                    try {
                        if (cursor.getCount() > 0 && !lastNameText.isEmpty() && !firstNameText.isEmpty() && !middleNameText.isEmpty() && !averageText.isEmpty()) {
                            int xint = provider.update(DatabaseDescription.Student.buildContactUri(iid),values,null,null);
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