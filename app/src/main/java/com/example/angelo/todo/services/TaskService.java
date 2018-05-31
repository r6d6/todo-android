package com.example.angelo.todo.services;

import android.util.Log;

import com.example.angelo.todo.database.TodoDatabaseHelper;
import com.example.angelo.todo.models.Task;
import com.example.angelo.todo.repositories.TaskRepository;

import java.util.ArrayList;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TodoDatabaseHelper database) {
        this.taskRepository = new TaskRepository(database);
    }

    public Task insert(String name) {
        Task task = new Task(name);

        long _id = this.taskRepository.insert(task);
        Long _idLong = new Long(_id);

        task.setId(_idLong.intValue());

        return task;
    }

    public void delete(long _id) {
        Long _idLong = new Long(_id);

        Task task = new Task("");
        task.setId(_idLong.intValue());

        this.taskRepository.delete(task);
    }

    public void update(long _id, String name) {
        Long _idLong = new Long(_id);

        Task task = new Task(name);
        task.setId(_idLong.intValue());

        this.taskRepository.update(task);
    }

    public ArrayList<Task> list() {
        return this.taskRepository.list();
    }

}
