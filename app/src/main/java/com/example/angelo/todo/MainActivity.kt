package com.example.angelo.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast

import java.util.ArrayList

import com.example.angelo.todo.database.DatabaseHelper
import com.example.angelo.todo.models.Task
import com.example.angelo.todo.partial.TaskListArrayAdapter
import com.example.angelo.todo.services.TaskService

import java.util.Collections

class MainActivity : AppCompatActivity() {

    private var taskList: ArrayList<Task>? = null
    private var taskListAdapter: TaskListArrayAdapter? = null
    private var databaseHelper: DatabaseHelper? = null
    private var taskService: TaskService? = null

    private val addTaskButton: ImageButton
        get() = findViewById(R.id.btnAddTask)

    private val newTaskEditText: EditText
        get() = findViewById(R.id.editTextNewTaskName)

    private val taskListView: ListView
        get() = findViewById(R.id.taskList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.boot()
    }

    private fun boot() {
        this.defineDatabase()
        this.taskService = TaskService(this.databaseHelper!!)

        this.setTaskListAdapter()
        this.startListeners()
    }

    private fun defineDatabase() {
        this.databaseHelper = DatabaseHelper(baseContext)
    }

    private fun setTaskListAdapter() {
        val taskListView = this.taskListView

        this.taskList = taskService!!.list()
        Collections.reverse(this.taskList!!)

        this.taskListAdapter = TaskListArrayAdapter(this.taskList!!, this, taskService!!)

        taskListView.adapter = taskListAdapter
    }

    private fun startListeners() {
        this.setAddTaskListener()
    }

    private fun setAddTaskListener() {
        val btnAddNewTask = this.addTaskButton

        val editTextTaskName = this.newTaskEditText
        val taskListView = this.taskListView

        btnAddNewTask.setOnClickListener(View.OnClickListener {
                if (editTextTaskName.text.toString() == "") {
                    Toast.makeText(this@MainActivity, R.string.new_task_message_when_empty, Toast.LENGTH_SHORT).show()

                    return@OnClickListener
                }

                val task = taskService!!.insert(editTextTaskName.text.toString())

                taskList!!.add(0, task)
                taskListView.adapter = taskListAdapter
                editTextTaskName.setText("")
        })
    }
}
