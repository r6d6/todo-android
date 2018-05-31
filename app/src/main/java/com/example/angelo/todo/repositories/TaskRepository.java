package com.example.angelo.todo.repositories;

import java.util.ArrayList;

import com.example.angelo.todo.database.TodoDatabaseHelper;
import com.example.angelo.todo.models.Task;

public class TaskRepository {

    TodoDatabaseHelper database;

    public TaskRepository(TodoDatabaseHelper database) {
        this.database = database;
    }

    public long insert(Task task) {
        return this.database.insertTask(task);
    }

    public void delete(Task task) {
        this.database.deleteTask(task);
    }

    public void update(Task task) {
        this.database.updateTask(task);
    }

    public ArrayList<Task> list() {
        ArrayList<Task> taskList = this.database.listTask();

        return taskList;
    }
}
