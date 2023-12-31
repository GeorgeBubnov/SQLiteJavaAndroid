package com.bubnov.lab5;

import static com.bubnov.lab5.MainActivity.provider;

import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bubnov.lab5.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class SecondFragment extends Fragment {
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText average;
    private Button buttonSave;

    public SecondFragment(){
        super(R.layout.fragment_second);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastName = view.findViewById(R.id.lastName);
        firstName = view.findViewById(R.id.firstName);
        middleName = view.findViewById(R.id.middleName);
        average = view.findViewById(R.id.average);
        buttonSave = view.findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastNameText = lastName.getText().toString();
                String firstNameText = firstName.getText().toString();
                String middleNameText = middleName.getText().toString();
                String averageText = average.getText().toString();

                if (!lastNameText.isEmpty() && !firstNameText.isEmpty() && !middleNameText.isEmpty() && !averageText.isEmpty()) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseDescription.Student.COLUMN_LASTNAME, lastNameText);
                    values.put(DatabaseDescription.Student.COLUMN_FIRSTNAME, firstNameText);
                    values.put(DatabaseDescription.Student.COLUMN_MIDDLENAME, middleNameText);
                    values.put(DatabaseDescription.Student.COLUMN_AVERAGE, averageText);

                    Uri xuri = provider.insert(DatabaseDescription.Student.CONTENT_URI,values);
                    MainActivity.last = averageText;
                    Toast.makeText(getActivity().getApplicationContext(), R.string.toastSuccess, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}