package com.bubnov.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SecondActivity extends AppCompatActivity {
    private TextInputEditText lastName;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText average;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        middleName = findViewById(R.id.middleName);
        average = findViewById(R.id.average);
        buttonSave = findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastNameText = lastName.getText().toString();
                String firstNameText = firstName.getText().toString();
                String middleNameText = middleName.getText().toString();
                String averageText = average.getText().toString();
                if (!lastNameText.isEmpty() && !firstNameText.isEmpty() && !middleNameText.isEmpty() && !averageText.isEmpty()) {
                    MainActivity.arr.add(lastNameText + " " + firstNameText + " " + middleNameText + " " + averageText);
                    MainActivity.last = averageText;
                    Toast.makeText(getApplicationContext(), R.string.toastSuccess, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.toastError), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}