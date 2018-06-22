package com.example.angelo.todo.mapper

import android.content.ContentValues

import com.example.angelo.todo.models.Task

import java.util.ArrayList
import java.util.function.Function

object TaskMapper {
    fun toEntity(_id: String, name: String): Task {
        val task = Task(name)

        val id = Integer.parseInt(_id)

        task.id = id

        return task
    }

    fun toEntity(name: String): Task {

        return Task(name)
    }

    fun toInsert(task: Task): ContentValues {
        val insertValues = ContentValues()
        insertValues.put("name", task.name)

        return insertValues
    }

    fun toList(taskDataList: ArrayList<ContentValues>): ArrayList<Task> {
        val taskList = ArrayList<Task>()

        taskDataList.stream().map { contentValues ->
            val task = Task(contentValues.getAsString("name"))
            task.id = contentValues.getAsInteger("_id")!!

            taskList.add(task)

            contentValues
        }

        return taskList
    }
}
