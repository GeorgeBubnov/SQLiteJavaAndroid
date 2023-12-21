// AddressBookContentProvider.java
// Субкласс ContentProvider для работы с базой данных приложения
package com.bubnov.lab3.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.bubnov.lab3.R;
import com.bubnov.lab3.database.DatabaseDescription.Student;

public class DatabaseContentProvider extends ContentProvider {
    // Используется для обращения к базе данных
    private DatabaseHelper dbHelper;

    // UriMatcher помогает ContentProvider определить выполняемую операцию
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Константы, используемые для определения выполняемой операции
    private static final int ONE_STUDENT = 1; // Один студент
    private static final int STUDENTS = 2; // Таблица студентов

    // Статический блок для настройки UriMatcher объекта ContentProvider
    static {
        // Uri для контакта с заданным идентификатором
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Student.TABLE_NAME + "/#", ONE_STUDENT);

        // Uri для таблицы
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, Student.TABLE_NAME, STUDENTS);
    }

    // Вызывается при создании ContentProvider
    @Override
    public boolean onCreate() {
        // Создание объекта DatabaseHelper
        dbHelper = new DatabaseHelper(getContext());
        return true; // Объект ContentProvider создан успешно
    }

    public void todo(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Обязательный метод: здесь не используется, возвращаем null
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // Получение информации из базы данных
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Создание SQLiteQueryBuilder для запроса к таблице students
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Student.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT: // Выбрать контакт с заданным идентификатором
                queryBuilder.appendWhere(
                        Student._ID + "=" + uri.getLastPathSegment());
                break;
            case STUDENTS: // Выбрать все контакты
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }

        // Выполнить запрос для получения одного или всех студентов
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);

        // Настройка отслеживания изменений в контенте
        //cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Вставка нового контакта в базу данных
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newStudentUri = null;

        switch (uriMatcher.match(uri)) {
            case STUDENTS:
                // При успехе возвращается идентификатор записи нового студента
                long rowId = dbHelper.getWritableDatabase().insert(
                        Student.TABLE_NAME, null, values);

                // Если студент был вставлен, создать подходящий Uri;
                // в противном случае выдать исключение
                if (rowId > 0) { // SQLite row IDs start at 1
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

    // Обновление существующего контакта в базе данных
    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int numberOfRowsUpdated; // 1, если обновление успешно; 0 при неудаче

        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT:
                // Получение идентификатора контакта из Uri
                String id = uri.getLastPathSegment();

                // Обновление контакта
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                        Student.TABLE_NAME, values, Student._ID + "=" + id,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_update_uri) + uri);
        }

        // Если были внесены изменения, оповестить наблюдателей
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsUpdated;
    }

    // Удаление существующего контакта из базы данных
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted;

        switch (uriMatcher.match(uri)) {
            case ONE_STUDENT:
                // Получение из URI идентификатора контакта
                String id = uri.getLastPathSegment();

                // Удаление контакта
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                        Student.TABLE_NAME, Student._ID + "=" + id, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_delete_uri) + uri);
        }

        // Оповестить наблюдателей об изменениях в базе данных
        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }
}
