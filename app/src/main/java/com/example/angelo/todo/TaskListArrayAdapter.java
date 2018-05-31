package com.example.angelo.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelo.todo.models.Task;
import com.example.angelo.todo.services.TaskService;

import java.util.ArrayList;

public class TaskListArrayAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Task> list;
    private Context context;
    final private TaskService taskService;

    public TaskListArrayAdapter(
            ArrayList<Task> list,
            Context context,
            TaskService taskService
    ) {
        this.list = list;
        this.context = context;
        this.taskService = taskService;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return this.list.get(pos).getId();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_task_list_item, null);

            TextView textViewTaskName = view.findViewById(R.id.textViewTaskName);

            textViewTaskName.setText(this.list.get(position).getName());
        }

        this.setListeners(view, position, parent.getRootView());

        return view;
    }

    protected void setListeners(View view, int position, View parentView) {
        this.setDeleteButtonListener(view, position, parentView);
        this.setSaveUpdateButtonListener(view, position, parentView);
        this.setEditTaskNameButtonListener(view, position, parentView);
    }

    private void setDeleteButtonListener(final View view, final int position, final View parentView) {
        final ImageButton deleteBtn = this.getDeleteButtonFromView(view);
        final TaskListArrayAdapter taskListArrayAdapter = this;

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                taskService.delete(list.get(position).getId());

                list.remove(position);

                taskListArrayAdapter.refreshData(parentView);
            }
        });
    }

    private void refreshData(View parentView) {
        this.notifyDataSetChanged();

        ListView taskListView = parentView.findViewById(R.id.taskList);
        taskListView.invalidateViews();

        taskListView.setAdapter(this);
    }

    private void setEditTaskNameButtonListener(final View view, final int position, final View parentView) {
        final ImageButton deleteBtn = this.getDeleteButtonFromView(view);
        final ImageButton editTaskNameBtn = this.getEditTaskNameButtonFromView(view);
        final TextView textViewTaskName = this.getTextViewTaskNameFromView(view);

        final TaskListArrayAdapter taskListArrayAdapter = this;

        editTaskNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTaskNameBtn.setVisibility(View.GONE);
                deleteBtn.setVisibility(View.GONE);
                textViewTaskName.setVisibility(View.GONE);

                String name = list.get(position).getName();

                EditText editTextUpdateName = view.findViewById(R.id.editTextUpdateName);
                editTextUpdateName.setText(name);

                LinearLayout layoutUpdateTaskElements = view.findViewById(R.id.linearLayoutUpdateTaskForm);
                layoutUpdateTaskElements.setVisibility(View.VISIBLE);

                taskListArrayAdapter.refreshData(parentView);
            }
        });
    }

    public ImageButton getDeleteButtonFromView(View view) {
        return view.findViewById(R.id.btnDeleteTask);
    }

    public ImageButton getSaveEditBtnFromView(View view) {
        return view.findViewById(R.id.btnSaveUpdate);
    }

    public ImageButton getEditTaskNameButtonFromView(View view) {
        return view.findViewById(R.id.btnEditTask);
    }

    public TextView getTextViewTaskNameFromView(View view) {
        return view.findViewById(R.id.textViewTaskName);
    }

    private void setSaveUpdateButtonListener(final View view, final int position, final View parentView) {
        final ImageButton deleteBtn = this.getDeleteButtonFromView(view);
        final ImageButton saveUpdateBtn = this.getSaveEditBtnFromView(view);
        final ImageButton editTaskNameBtn = this.getEditTaskNameButtonFromView(view);
        final TextView textViewTaskName = this.getTextViewTaskNameFromView(view);

        final TaskListArrayAdapter taskListArrayAdapter = this;

        saveUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUpdateName = view.findViewById(R.id.editTextUpdateName);
                String newName = editTextUpdateName.getText().toString();

                if(newName.equals("")) {
                    Toast.makeText(taskListArrayAdapter.context, R.string.update_task_message_when_empty, Toast.LENGTH_SHORT).show();

                    return;
                }

                Task task = list.get(position);
                task.setName(newName);

                taskService.update(task.getId(), task.getName());

                list.set(position, task);

                LinearLayout layoutUpdateTaskElements = view.findViewById(R.id.linearLayoutUpdateTaskForm);
                layoutUpdateTaskElements.setVisibility(View.GONE);

                editTaskNameBtn.setVisibility(View.VISIBLE);
                deleteBtn.setVisibility(View.VISIBLE);
                textViewTaskName.setVisibility(View.VISIBLE);

                taskListArrayAdapter.refreshData(parentView);
            }
        });
    }
}

