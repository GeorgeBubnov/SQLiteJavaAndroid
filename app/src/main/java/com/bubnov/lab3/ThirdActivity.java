package com.bubnov.lab3;

import static com.bubnov.lab3.MainActivity.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bubnov.lab3.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class ThirdActivity extends AppCompatActivity {
    private TextInputEditText id;
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText average;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        id = findViewById(R.id.idtext);
        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        average = findViewById(R.id.average);
        buttonSave = findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = id.getText().toString();
                if(!sid.isEmpty()) {
                    long iid = Integer.parseInt(sid);

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
                            Toast.makeText(getApplicationContext(), R.string.toastSuccess, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                        }
                    } finally {
                        cursor.close();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}