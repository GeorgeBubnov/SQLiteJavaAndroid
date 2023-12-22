package com.bubnov.lab3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bubnov.lab3.database.DatabaseDescription.Student;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NoteBook.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_STUDENTS_TABLE =
                "CREATE TABLE " + Student.TABLE_NAME + "(" +
                        Student._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Student.COLUMN_LASTNAME + " TEXT, " +
                        Student.COLUMN_FIRSTNAME + " TEXT, " +
                        Student.COLUMN_MIDDLENAME + " TEXT, " +
                        Student.COLUMN_AVERAGE + " TEXT);";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME);
        onCreate(db);
    }
}
