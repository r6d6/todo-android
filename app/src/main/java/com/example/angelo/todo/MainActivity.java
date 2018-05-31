package com.example.angelo.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.angelo.todo.database.TodoDatabaseHelper;
import com.example.angelo.todo.models.Task;
import com.example.angelo.todo.services.TaskService;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> taskList;

    private TaskListArrayAdapter taskListAdapter;

    private TodoDatabaseHelper todoDatabaseHelper;

    private TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.migrateDatabase();
        this.setTaskListAdapter();
        this.setListeners();

        this.taskService = new TaskService(this.todoDatabaseHelper);
    }

    protected void migrateDatabase() {
        this.todoDatabaseHelper = new TodoDatabaseHelper(getBaseContext());
    }

    private void setTaskListAdapter() {
        ListView taskListView = this.getTaskListView();

        this.taskList = taskService.list();
        Collections.reverse(this.taskList);

        this.taskListAdapter = new TaskListArrayAdapter(this.taskList, this, taskService);

        taskListView.setAdapter(taskListAdapter);
    }

    protected void setListeners() {
        this.setAddTaskListener();
    }

    protected void setAddTaskListener() {
        ImageButton btnAddNewTask = this.getAddTaskButton();

        final EditText editTextTaskName = this.getNewTaskEditText();
        final ListView taskListView = this.getTaskListView();

        btnAddNewTask.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(editTextTaskName.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, R.string.new_task_message_when_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = taskService.insert(editTextTaskName.getText().toString());

            taskList.add(0, task);
            taskListView.setAdapter(taskListAdapter);
            editTextTaskName.setText("");
            }
        });
    }

    private ImageButton getAddTaskButton() {
        ImageButton btnAddNewTask = this.findViewById(R.id.btnAddTask);

        return btnAddNewTask;
    }


    private EditText getNewTaskEditText() {
        EditText editTextTaskName = this.findViewById(R.id.editTextNewTaskName);

        return editTextTaskName;
    }

    private ListView getTaskListView() {
        ListView taskListView = this.findViewById(R.id.taskList);

        return taskListView;
    }
}
