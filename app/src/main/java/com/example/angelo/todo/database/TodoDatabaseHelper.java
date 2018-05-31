package com.example.angelo.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import com.example.angelo.todo.models.Task;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int VERSION = 1;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.createTaskTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks;");
        onCreate(db);
    }

    private void createTaskTable(SQLiteDatabase db) {
        db.execSQL(this.getCreateTaskTableSql());
    }

    private String getCreateTaskTableSql() {
        String table = "tasks";
        String sql = "CREATE TABLE ";

        StringBuilder sqlStringBuilder = new StringBuilder(sql);

        sqlStringBuilder.append(table).append("(")
                .append("_id INTEGER PRIMARY KEY AUTOINCREMENT").append(", ")
                .append("name TEXT").append(", ")
                .append("created_at TEXT").append(");");

        return sqlStringBuilder.toString();
    }

    public long insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues insertValues = new ContentValues();
        insertValues.put("name", task.getName());

        long id = db.insert("tasks", null, insertValues);

        return id;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues updateValues = new ContentValues();
        updateValues.put("_id", Integer.toString(task.getId()));
        updateValues.put("name", task.getName());

        db.update("tasks", updateValues, "_id=?", new String[]{Integer.toString(task.getId())});

        db.close();
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("tasks", "_id=?", new String[]{Integer.toString(task.getId())});

        db.close();
    }

    public ArrayList<Task> listTask() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Task> taskList = new ArrayList<Task>();

        Cursor cursor = db.query("tasks", new String[] {"_id", "name"}, null, null, null, null, "_id ASC");

        if(cursor.moveToFirst()) {
            while(cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));

                Task task = new Task(name);
                task.setId(Integer.parseInt(_id));

                taskList.add(task);
            }
        }

        cursor.close();
        db.close();

        return taskList;
    }

}
