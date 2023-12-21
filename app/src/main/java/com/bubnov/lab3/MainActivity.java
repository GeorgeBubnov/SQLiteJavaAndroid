package com.bubnov.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bubnov.lab3.database.DatabaseDescription.*;
import com.bubnov.lab3.database.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> arr = new ArrayList<String>();
    public static String last = "";
    public static ArrayList<String> projection = new ArrayList<String>();//{""}
    public static int NOTIFICATION_ID = 101;
    public static String CHANNEL_ID = "channelID";
    ListView listView;
    Button buttonAdd;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        buttonAdd = findViewById(R.id.buttonAdd);

        helper = new DatabaseHelper(this);

        arr.add("s");



        /*String[] strings = new String[4];
        strings[0] = Student.COLUMN_LASTNAME;
        strings[1] = Student.COLUMN_FIRSTNAME;
        strings[2] = Student.COLUMN_MIDDLENAME;
        strings[3] = Student.COLUMN_AVERAGE;*/

        /*DatabaseContentProvider db = new DatabaseContentProvider();
        db.onCreate();
        db.hel
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(, MODE_PRIVATE, null);*/


        //Cursor cur = db.query(Student.CONTENT_URI,strings,null,null,null);
        //cursor.getString(cursor.getColumnIndex(Student.COLUMN_NAME));
        //arr.add(cur.getColumnName(1));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);*/

                /*DatabaseContentProvider dbcont = new DatabaseContentProvider();
                // Создайте новую строку со значениями для вставки.
                ContentValues newValues = new ContentValues();
                // Задайте значения для каждой строки.
                newValues.put(Student.COLUMN_LASTNAME, "Bubnov");
                //[ ... Повторите для каждого столбца ... ]
                // Вставьте строку в вашу базу данных.
                dbcont.insert(Student.CONTENT_URI, newValues);
                //dbcont.insert(DATABASE_TABLE, null, newValues);*/


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        insertStudent();
        displayDatabaseInfo();
    }



    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = helper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                Student._ID,
                Student.COLUMN_LASTNAME,
                Student.COLUMN_FIRSTNAME,
                Student.COLUMN_MIDDLENAME,
                Student.COLUMN_AVERAGE
        };

        // Делаем запрос
        Cursor cursor = db.query(
                Student.TABLE_NAME,    // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null                   // порядок сортировки
        );

        try {
            arr.add("Таблица содержит " + cursor.getCount() + " гостей");
            arr.add(Student._ID + " - " +
                    Student.COLUMN_FIRSTNAME + " - " +
                    Student.COLUMN_LASTNAME + " - " +
                    Student.COLUMN_MIDDLENAME + " - " +
                    Student.COLUMN_AVERAGE
            );

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(Student._ID);
            int lnColumnIndex = cursor.getColumnIndex(Student.COLUMN_LASTNAME);
            int fnColumnIndex = cursor.getColumnIndex(Student.COLUMN_FIRSTNAME);
            int mnColumnIndex = cursor.getColumnIndex(Student.COLUMN_MIDDLENAME);
            int avColumnIndex = cursor.getColumnIndex(Student.COLUMN_AVERAGE);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentLN = cursor.getString(lnColumnIndex);
                String currentFN = cursor.getString(fnColumnIndex);
                String currentMN = cursor.getString(mnColumnIndex);
                String currentAV = cursor.getString(avColumnIndex);
                // Выводим значения каждого столбца
                arr.add((currentID + " - " +
                        currentLN + " - " +
                        currentFN + " - " +
                        currentMN + " - " +
                        currentAV));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    private void insertStudent() {

        // Gets the database in write mode
        SQLiteDatabase db = helper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о студенте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_LASTNAME, "Бубнов");
        values.put(Student.COLUMN_FIRSTNAME, "Георгий");
        values.put(Student.COLUMN_MIDDLENAME, "Владимирович");
        values.put(Student.COLUMN_AVERAGE, "99");

        long newRowId = db.insert(Student.TABLE_NAME, null, values);
    }
}