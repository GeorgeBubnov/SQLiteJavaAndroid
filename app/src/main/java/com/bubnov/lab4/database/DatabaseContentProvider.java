// AddressBookContentProvider.java
// Субкласс ContentProvider для работы с базой данных приложения
package com.bubnov.lab4.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.bubnov.lab4.R;
import com.bubnov.lab4.database.DatabaseDescription.Student;

public class DatabaseContentProvider extends ContentProvider {
    private DatabaseHelper dbHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ONE_STUDENT = 1;
    private static final int STUDENTS = 2;
    static {
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Student.TABLE_NAME + "/#", ONE_STUDENT);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Student.TABLE_NAME, STUDENTS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }
    public void todo(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Student.TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT:
                queryBuilder.appendWhere(
                        Student._ID + "=" + uri.getLastPathSegment());
                break;
            case STUDENTS:
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newStudentUri = null;
        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                long rowId = dbHelper.getWritableDatabase().insert(
                        Student.TABLE_NAME, null, values);

                if (rowId > 0) {
                    newStudentUri = Student.buildContactUri(rowId);
                } else
                    throw new SQLException(
                            getContext().getString(R.string.insert_failed) + uri);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_insert_uri) + uri);
        }
        return newStudentUri;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int numberOfRowsUpdated;
        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT:
                String id = uri.getLastPathSegment();
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                        Student.TABLE_NAME, values, Student._ID + "=" + id,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_update_uri) + uri);
        }
        return numberOfRowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted;
        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT:
                String id = uri.getLastPathSegment();
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        Student.TABLE_NAME, Student._ID + "=" + id, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_delete_uri) + uri);
        }
        return numberOfRowsDeleted;
    }
}