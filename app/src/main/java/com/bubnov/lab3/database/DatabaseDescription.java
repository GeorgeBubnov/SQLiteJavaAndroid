package com.bubnov.lab3.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
    public static final String AUTHORITY = "com.bubnov.lab3.database";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Student implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_LASTNAME = "lastname";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_MIDDLENAME = "middlename";
        public static final String COLUMN_AVERAGE = "average";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
