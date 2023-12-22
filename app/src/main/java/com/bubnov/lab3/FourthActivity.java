package com.bubnov.lab3;

import static com.bubnov.lab3.MainActivity.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bubnov.lab3.database.DatabaseDescription;
import com.google.android.material.textfield.TextInputEditText;

public class FourthActivity extends AppCompatActivity {
    private TextInputEditText id;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        id = findViewById(R.id.idtext);
        buttonSave = findViewById(R.id.buttonAdd);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = id.getText().toString();
                if(!sid.isEmpty()) {
                    long iid = Integer.parseInt(sid);

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