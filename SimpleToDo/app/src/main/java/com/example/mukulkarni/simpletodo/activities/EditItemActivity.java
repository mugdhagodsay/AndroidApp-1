package com.example.mukulkarni.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mukulkarni.simpletodo.R;
import com.example.mukulkarni.simpletodo.fragments.DatePickerFragment;
import com.example.mukulkarni.simpletodo.todo.Task;

public class EditItemActivity extends BaseActivity {
    private TextView textView;
    private EditText task;
    private EditText priority;
    private EditText notes;
    private Button dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.edit_activity);
        setContentView(R.layout.activity_edit_item);
        task = (EditText) findViewById(R.id.et_edit_item);
        priority = (EditText) findViewById(R.id.et_edit_priority);
        notes = (EditText) findViewById(R.id.et_edit_notes);
        dueDate = (Button) findViewById(R.id.btn_date);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //Display item's initial value
            Task taskTobeEdited = (Task)bundle.getSerializable("task");
            task.setText(taskTobeEdited.getTask());
            priority.setText(taskTobeEdited.getPriority());
            notes.setText(taskTobeEdited.getNotes());
            dueDate.setText(taskTobeEdited.getDueDate());

            //Set user's cursor in the text field is at the end of the current text value
            task.setSelection(taskTobeEdited.getTask().length());
        } else {
            task.setText("");
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // User chose the "Settings" item, show the app settings UI...
                int position = 0;
                String originalText = "";
                Intent originalIntent = getIntent();
                Bundle bundle = originalIntent.getExtras();
                Task originalTask = (Task) bundle.getSerializable("task");
                if (bundle != null) {
                    position = (Integer) bundle.get("position");
                    originalText = originalTask.getTask();
                }
                // Prepare data intent
                Intent data = new Intent();
                Task updatedTask = new Task();
                updatedTask.setTask(task.getText().toString());
                updatedTask.setPriority(priority.getText().toString());
                updatedTask.setNotes(notes.getText().toString());
                updatedTask.setDueDate(dueDate.getText().toString());
                // Pass relevant data back as a result
                data.putExtra("originalTask", originalText);
                data.putExtra("task", updatedTask);
                data.putExtra("code", 200); // ints work too
                data.putExtra("position", position);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void onSaveItem(View v) {
        int position = 0;
        String originalText = "";
        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        Task originalTask = (Task) bundle.getSerializable("task");
        if (bundle != null) {
            position = (Integer) bundle.get("position");
            originalText = originalTask.getTask();
        }
        // Prepare data intent
        Intent data = new Intent();
        Task updatedTask = new Task();
        updatedTask.setTask(task.getText().toString());
        updatedTask.setPriority(priority.getText().toString());
        updatedTask.setNotes(notes.getText().toString());
        updatedTask.setDueDate(dueDate.getText().toString());
        // Pass relevant data back as a result
        data.putExtra("originalTask", originalText);
        data.putExtra("task", updatedTask);
        data.putExtra("code", 200); // ints work too
        data.putExtra("position", position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
