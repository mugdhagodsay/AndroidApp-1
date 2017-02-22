package com.example.mukulkarni.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mukulkarni.simpletodo.R;
import com.example.mukulkarni.simpletodo.todo.Task;

import java.util.ArrayList;

/**
 * Created by mukulkarni on 2/20/17.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks)
    {
        super(context, 0, tasks);
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Task task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_row, parent, false);

        }

        // Lookup view for data population

        TextView tvTask = (TextView) convertView.findViewById(R.id.task_name);


        TextView tvDate = (TextView) convertView.findViewById(R.id.displayDate);

        TextView tvPriority = (TextView) convertView.findViewById(R.id.task_priority);

        // Populate the data into the template view using the data object

        tvTask.setText(task.getTask());
        tvDate.setText(task.getDueDate());
        tvPriority.setText(task.getPriority());

//        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => " + c.getTime());
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());
//
//        tvDate.setText(formattedDate);

        // Return the completed view to render on screen

        return convertView;

    }
}
