package com.example.angelo.todo.repositories

import java.util.ArrayList

import com.example.angelo.todo.database.DatabaseHelper
import com.example.angelo.todo.mapper.TaskMapper
import com.example.angelo.todo.models.Task

class TaskRepository(private val database: DatabaseHelper) {
    fun insert(task: Task): Task {
        val _id = this.database.insertTask(TaskMapper.toInsert(task))

        task.id = _id.toInt()

        return task
    }

    fun delete(task: Task) {
        this.database.deleteTask(task.id)
    }

    fun update(task: Task): Task {
        this.database.updateTask(task.id, task.name!!)

        return task
    }

    fun list(): ArrayList<Task> {
        val taskDataList = this.database.listTask()

        return TaskMapper.toList(taskDataList)
    }
}
