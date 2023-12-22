package com.bubnov.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bubnov.lab3.database.DatabaseContentProvider;
import com.bubnov.lab3.database.DatabaseDescription.*;
import com.bubnov.lab3.database.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> arr = new ArrayList<String>();
    public static String last = "";
    public static int NOTIFICATION_ID = 101;
    public static String CHANNEL_ID = "channelID";
    ListView listView;
    Button buttonAdd;
    Button buttonUpdate;
    Button buttonDelete;
    public DatabaseHelper helper;
    public static DatabaseContentProvider provider = new DatabaseContentProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        helper = new DatabaseHelper(this);
        provider.todo(this);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        arr = new ArrayList<String>();
        displayDatabaseInfo();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        if (!arr.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(getResources().getString(R.string.notiTitle))
                    .setContentText(getResources().getString(R.string.notiVar) + " " + last + "\n" + getResources().getString(R.string.notiText) + " " + Integer.toString(arr.size()))
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSilent(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
    private void displayDatabaseInfo() {
        String[] projection = {
                Student._ID,
                Student.COLUMN_LASTNAME,
                Student.COLUMN_FIRSTNAME,
                Student.COLUMN_MIDDLENAME,
                Student.COLUMN_AVERAGE
        };
        Cursor cursor = provider.query(Student.CONTENT_URI, projection,null,null,null);
        try {
            arr.add(Student._ID + " - " +
                    Student.COLUMN_FIRSTNAME + " - " +
                    Student.COLUMN_LASTNAME + " - " +
                    Student.COLUMN_MIDDLENAME + " - " +
                    Student.COLUMN_AVERAGE
            );

            int idColumnIndex = cursor.getColumnIndex(Student._ID);
            int lnColumnIndex = cursor.getColumnIndex(Student.COLUMN_LASTNAME);
            int fnColumnIndex = cursor.getColumnIndex(Student.COLUMN_FIRSTNAME);
            int mnColumnIndex = cursor.getColumnIndex(Student.COLUMN_MIDDLENAME);
            int avColumnIndex = cursor.getColumnIndex(Student.COLUMN_AVERAGE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentLN = cursor.getString(lnColumnIndex);
                String currentFN = cursor.getString(fnColumnIndex);
                String currentMN = cursor.getString(mnColumnIndex);
                String currentAV = cursor.getString(avColumnIndex);
                arr.add((currentID + " - " +
                        currentLN + " - " +
                        currentFN + " - " +
                        currentMN + " - " +
                        currentAV));
            }
        } finally {
            cursor.close();
        }
    }
}