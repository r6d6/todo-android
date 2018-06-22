package com.example.angelo.todo.partial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast

import com.example.angelo.todo.R
import com.example.angelo.todo.models.Task
import com.example.angelo.todo.services.TaskService

import java.util.ArrayList

class TaskListArrayAdapter(
        private val list: ArrayList<Task>,
        private val context: Context,
        private val taskService: TaskService
) : BaseAdapter(), ListAdapter {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(pos: Int): Any {
        return list[pos]
    }

    override fun getItemId(pos: Int): Long {
        return this.list[pos].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view: View? = convertView

        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.activity_task_list_item, null)
        }

        this.getTextViewTaskNameFromView(view!!).text = this.list[position].name

        this.setListeners(view, position, parent.rootView)

        return view
    }

    private fun refreshData() {
        this.notifyDataSetChanged()
    }

    private fun setListeners(view: View, position: Int, parentView: View) {
        this.setDeleteButtonListener(view, position, parentView)
        this.setSaveUpdateButtonListener(view, position, parentView)
        this.setEditTaskNameButtonListener(view, position, parentView)
    }

    private fun setDeleteButtonListener(view: View, position: Int, parentView: View) {
        val deleteBtn = Companion.getDeleteButtonFromView(view)
        val taskListArrayAdapter = this

        deleteBtn.setOnClickListener {
            taskService.delete(list[position].id.toLong())

            list.removeAt(position)

            taskListArrayAdapter.refreshData()
        }
    }

    private fun setEditTaskNameButtonListener(view: View, position: Int, parentView: View) {
        val taskNameTextView = this.getTextViewTaskNameFromView(view)
        val deleteButton = Companion.getDeleteButtonFromView(view)
        val editTaskButton = this.getEditTaskNameButtonFromView(view)
        val updateTaskNameEditText = this.getEditTextUpdateNameFromView(view)
        val saveChangeButton = this.getSaveEditBtnFromView(view)

        editTaskButton.setOnClickListener {
            deleteButton.visibility = View.GONE
            editTaskButton.visibility = View.GONE
            taskNameTextView.visibility = View.GONE

            val task = list[position]
            updateTaskNameEditText.setText(task.name)

            saveChangeButton.visibility = View.VISIBLE
            updateTaskNameEditText.visibility = View.VISIBLE
        }
    }

    private fun setSaveUpdateButtonListener(view: View, position: Int, parentView: View) {
        val deleteBtn = Companion.getDeleteButtonFromView(view)
        val saveUpdateBtn = this.getSaveEditBtnFromView(view)
        val editTaskNameBtn = this.getEditTaskNameButtonFromView(view)
        val textViewTaskName = this.getTextViewTaskNameFromView(view)
        val editTextUpdateName = view.findViewById<EditText>(R.id.editTextUpdateName)
        val taskListArrayAdapter = this

        saveUpdateBtn.setOnClickListener(View.OnClickListener {
            val newName = editTextUpdateName.text.toString()

            if (newName == "") {
                Toast.makeText(taskListArrayAdapter.context, R.string.update_task_message_when_empty, Toast.LENGTH_SHORT).show()

                return@OnClickListener
            }

            val task = list[position]
            task.name = newName
            taskService.update(task.id.toLong(), task.name!!)
            list[position] = task

            saveUpdateBtn.visibility = View.GONE
            editTextUpdateName.visibility = View.GONE

            editTaskNameBtn.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE
            textViewTaskName.visibility = View.VISIBLE

            taskListArrayAdapter.refreshData()
        })
    }

    private fun getSaveEditBtnFromView(view: View): ImageButton {
        return view.findViewById(R.id.btnSaveUpdate)
    }

    private fun getEditTaskNameButtonFromView(view: View): ImageButton {
        return view.findViewById(R.id.btnEditTask)
    }

    private fun getEditTextUpdateNameFromView(view: View): EditText {
        return view.findViewById(R.id.editTextUpdateName)
    }

    private fun getTextViewTaskNameFromView(view: View): TextView {
        return view.findViewById(R.id.textViewTaskName)
    }

    companion object {
        fun getDeleteButtonFromView(view: View): ImageButton {
            return view.findViewById(R.id.btnDeleteTask)
        }
    }
}

