package com.example.lost_and_found;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private SQLiteDatabase db;

    public TaskDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, task.getName());
        values.put(DBHelper.COLUMN_PHONE, task.getPhone());
        values.put(DBHelper.COLUMN_DESCRIPTION, task.getDescription());
        values.put(DBHelper.COLUMN_DATE, task.getDate());
        values.put(DBHelper.COLUMN_LOCATION, task.getLocation());
        values.put(DBHelper.COLUMN_TYPE, task.getType());
        return db.insert(DBHelper.TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
                task.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
                task.setDate(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                task.setLocation(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LOCATION)));
                task.setType(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TYPE)));

                tasks.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tasks;
    }

    @SuppressLint("Range")
    public Task getTask(int id) {
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, DBHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
            task.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
            task.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
            task.setDate(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
            task.setLocation(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_LOCATION)));
            task.setType(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TYPE)));

            cursor.close();
            return task;
        }
        return null;
    }

    public void deleteTask(int id) {
        db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
