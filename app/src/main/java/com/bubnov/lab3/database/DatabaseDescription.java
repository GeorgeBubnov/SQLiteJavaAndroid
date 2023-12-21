// DatabaseDescription.java
// Класс описывает имя таблицы и имена столбцов базы данных, а также
// содержит другую информацию, необходимую для ContentProvider
package com.bubnov.lab3.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
    /*// Имя ContentProvider: обычно совпадает с именем пакета
    public static final String AUTHORITY = "com.bubnov.lab3.database";
    // Базовый URI для взаимодействия с ContentProvider
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);*/
    public static final class Student implements BaseColumns {
        public static final String TABLE_NAME = "students"; // Имя таблицы
        public static final String COLUMN_LASTNAME = "lastname";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_MIDDLENAME = "middlename";
        public static final String COLUMN_AVERAGE = "average";

        /*// Объект Uri для таблицы students
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();*/

        /*// Создание Uri для конкретного студента
        public static Uri buildContactUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }*/
    }
}
