package com.bubnov.lab5;

import static com.bubnov.lab5.MainActivity.CHANNEL_ID;
import static com.bubnov.lab5.MainActivity.NOTIFICATION_ID;
import static com.bubnov.lab5.MainActivity.arr;
import static com.bubnov.lab5.MainActivity.last;
import static com.bubnov.lab5.MainActivity.provider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bubnov.lab5.database.DatabaseDescription;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    public FirstFragment(){
        super(R.layout.fragment_first);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listView);

        arr = new ArrayList<String>();
        displayDatabaseInfo();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        if (!arr.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(getResources().getString(R.string.notiTitle))
                    .setContentText(getResources().getString(R.string.notiVar) + " " + last + "\n" + getResources().getString(R.string.notiText) + " " + Integer.toString(arr.size()))
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSilent(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void displayDatabaseInfo() {
        String[] projection = {
                DatabaseDescription.Student._ID,
                DatabaseDescription.Student.COLUMN_LASTNAME,
                DatabaseDescription.Student.COLUMN_FIRSTNAME,
                DatabaseDescription.Student.COLUMN_MIDDLENAME,
                DatabaseDescription.Student.COLUMN_AVERAGE
        };
        Cursor cursor = provider.query(DatabaseDescription.Student.CONTENT_URI, projection,null,null,null);
        try {
            arr.add(DatabaseDescription.Student._ID + " - " +
                    DatabaseDescription.Student.COLUMN_FIRSTNAME + " - " +
                    DatabaseDescription.Student.COLUMN_LASTNAME + " - " +
                    DatabaseDescription.Student.COLUMN_MIDDLENAME + " - " +
                    DatabaseDescription.Student.COLUMN_AVERAGE
            );

            int idColumnIndex = cursor.getColumnIndex(DatabaseDescription.Student._ID);
            int lnColumnIndex = cursor.getColumnIndex(DatabaseDescription.Student.COLUMN_LASTNAME);
            int fnColumnIndex = cursor.getColumnIndex(DatabaseDescription.Student.COLUMN_FIRSTNAME);
            int mnColumnIndex = cursor.getColumnIndex(DatabaseDescription.Student.COLUMN_MIDDLENAME);
            int avColumnIndex = cursor.getColumnIndex(DatabaseDescription.Student.COLUMN_AVERAGE);

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