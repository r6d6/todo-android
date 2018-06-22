package com.example.angelo.todo.services

import com.example.angelo.todo.database.DatabaseHelper
import com.example.angelo.todo.models.Task
import com.example.angelo.todo.repositories.TaskRepository
import com.example.angelo.todo.mapper.TaskMapper

import java.util.ArrayList

class TaskService(database: DatabaseHelper) {

    private val taskRepository: TaskRepository = TaskRepository(database)

    fun insert(name: String): Task {
        return taskRepository.insert(TaskMapper.toEntity(name))
    }

    fun delete(_id: Long) {
        this.taskRepository.delete(TaskMapper.toEntity(java.lang.Long.toString(_id), ""))
    }

    fun update(_id: Long, name: String): Task {
        return taskRepository.update(TaskMapper.toEntity(java.lang.Long.toString(_id), name))
    }

    fun list(): ArrayList<Task> {
        return this.taskRepository.list()
    }
}
