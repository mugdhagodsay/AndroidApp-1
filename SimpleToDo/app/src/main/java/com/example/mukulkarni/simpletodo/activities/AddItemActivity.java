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

import com.example.mukulkarni.simpletodo.R;
import com.example.mukulkarni.simpletodo.fragments.DatePickerFragment;
import com.example.mukulkarni.simpletodo.todo.Task;

/**
 * Created by mukulkarni on 2/21/17.
 */

public class AddItemActivity extends BaseActivity {
    private EditText addTask;
    private EditText priority;
    private EditText notes;
    private Button dueDate;
    private int REQUEST_DATE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add_item_activity);
        setContentView(R.layout.activity_add_item);
        addTask = (EditText) findViewById(R.id.et_add_new_item);
        priority = (EditText) findViewById(R.id.et_priority);
        notes = (EditText) findViewById(R.id.et_notes);
        dueDate = (Button) findViewById(R.id.btn_date);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
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
                Intent originalIntent = getIntent();
                Bundle bundle = originalIntent.getExtras();
                // Prepare data intent
                Intent data = new Intent();
                Task task = new Task();
                task.setTask(addTask.getText().toString());
                task.setPriority(priority.getText().toString());
                task.setNotes(notes.getText().toString());
                task.setDueDate(dueDate.getText().toString());
                // Pass relevant data back as a result
                data.putExtra("map", task);
                data.putExtra("code", 200); // ints work too
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
        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        // Prepare data intent
        Intent data = new Intent();
        Task task = new Task();
        task.setTask(addTask.getText().toString());
        task.setPriority(priority.getText().toString());
        task.setNotes(notes.getText().toString());
        task.setDueDate(dueDate.getText().toString());
        // Pass relevant data back as a result
        data.putExtra("map", task);
        data.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
