package com.bubnov.lab4;

import static com.bubnov.lab4.MainActivity.provider;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bubnov.lab4.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class SecondFragment extends Fragment {
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText average;
    private TextInputEditText attendance;
    private TextInputEditText email;
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
        attendance = view.findViewById(R.id.attendance);
        email = view.findViewById(R.id.email);
        buttonSave = view.findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastNameText = lastName.getText().toString();
                String firstNameText = firstName.getText().toString();
                String middleNameText = middleName.getText().toString();
                String averageText = average.getText().toString();
                String attendanceText = attendance.getText().toString();
                String emailText = email.getText().toString();

                if (!lastNameText.isEmpty() && !firstNameText.isEmpty() && !middleNameText.isEmpty() && !averageText.isEmpty()) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseDescription.Student.COLUMN_LASTNAME, lastNameText);
                    values.put(DatabaseDescription.Student.COLUMN_FIRSTNAME, firstNameText);
                    values.put(DatabaseDescription.Student.COLUMN_MIDDLENAME, middleNameText);
                    values.put(DatabaseDescription.Student.COLUMN_AVERAGE, averageText);
                    values.put(DatabaseDescription.Student.COLUMN_ATTENDANCE, attendanceText);
                    values.put(DatabaseDescription.Student.COLUMN_EMAIL, emailText);

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