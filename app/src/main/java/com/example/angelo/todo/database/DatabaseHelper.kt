package com.example.angelo.todo.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    private val createTaskTableSql: String
        get() {
            val table = "tasks"
            val sql = "CREATE TABLE "

            return sql + table + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                    "name TEXT" + ", " +
                    "created_at TEXT" + ");"
        }

    override fun onCreate(db: SQLiteDatabase) {
        this.createTaskTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tasks;")
        onCreate(db)
    }

    private fun createTaskTable(db: SQLiteDatabase) {
        db.execSQL(this.createTaskTableSql)
    }

    fun insertTask(insertValues: ContentValues): Long {
        val db = this.writableDatabase

        return db.insert("tasks", null, insertValues)
    }

    fun updateTask(_id: Int, name: String) {
        val db = this.writableDatabase

        val updateValues = ContentValues()
        updateValues.put("_id", Integer.toString(_id))
        updateValues.put("name", name)

        db.update("tasks", updateValues, "_id=?", arrayOf(Integer.toString(_id)))

        db.close()
    }

    fun deleteTask(_id: Int) {
        val db = this.writableDatabase

        db.delete("tasks", "_id=?", arrayOf(Integer.toString(_id)))

        db.close()
    }

    fun listTask(): ArrayList<ContentValues> {
        val db = this.writableDatabase

        val taskDataList = ArrayList<ContentValues>()

        val cursor = db.query("tasks", arrayOf("_id", "name"), null, null, null, null, "_id ASC")

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                val taskData = ContentValues()
                taskData.put("_id", cursor.getString(cursor.getColumnIndex("_id")))
                taskData.put("name", cursor.getString(cursor.getColumnIndex("name")))

                taskDataList.add(taskData)
            }
        }

        cursor.close()
        db.close()

        return taskDataList
    }

    companion object {
        private val DATABASE_NAME = "todo.db"
        private val VERSION = 1
    }

}
