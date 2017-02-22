package com.example.mukulkarni.simpletodo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mukulkarni.simpletodo.R;
import com.example.mukulkarni.simpletodo.adapters.TaskAdapter;
import com.example.mukulkarni.simpletodo.todo.Task;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import static com.example.mukulkarni.simpletodo.todo.Task_Table.task;

/**
 * @author Mugdha Kulkarni
 */
public class MainActivity extends BaseActivity {

    ArrayList<Task> arrayOfTasks;
    TaskAdapter taskAdapter;
    ListView lvItems;
    Button buttonAdd;
    private final int REQUEST_EDIT_ITEM = 200;
    private final int REQUEST_ADD_ITEM = 201;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View saveView = toolbar.findViewById(R.id.action_save);
        lvItems = (ListView) findViewById(R.id.lvItems);
        arrayOfTasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, arrayOfTasks);
        readFromDb();
        lvItems.setAdapter(taskAdapter);
        setupListViewListener();
           // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // User chose the "Settings" item, show the app settings UI...
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("requestCode", REQUEST_ADD_ITEM);
                startActivityForResult(i, REQUEST_ADD_ITEM);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Set up ListView listeners for click and longClick
     * a click will let user edit the clicked item
     * a long click will delete the clicked item from the list
     */
    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        i.putExtra("position", pos);
                        i.putExtra("task", taskAdapter.getItem(pos));
                        i.putExtra("requestCode", REQUEST_EDIT_ITEM);
                        startActivityForResult(i, REQUEST_EDIT_ITEM);
                    }
                }
        );
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        showAlert(pos);
                        return true;
                    }
                });
    }

    /**
     * Show Alert dialogue when the user is trying to delete an item
     * @param pos
     */
    public void showAlert(int pos)
    {
        final int position = pos;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.delete_message)
                .setTitle(R.string.delete_title);
        // Add the buttons
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String itemDeleted = arrayOfTasks.get(position).getTask();
                arrayOfTasks.remove(position);
                taskAdapter.notifyDataSetChanged();
                deleteFromDb(itemDeleted);                            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Read tasks from the Database
    private void readFromDb() {
        try {
            List<Task> taskList = SQLite.select().from(Task.class).queryList();
            for (Task task : taskList) {
                System.out.println("Task to be added is: " + task.getTask());
                arrayOfTasks.add(task);
            }
        } catch (Exception e) {
            arrayOfTasks = new ArrayList<Task>();
        }
    }

    //Update database
    private void updateDb(String item, Task updatedTask) {
        deleteFromDb(item);
        updatedTask.save();
    }

    private void deleteFromDb(String item) {
        Task tasks = SQLite.select().
                from(Task.class).
                where(task.is(item)).querySingle();
        if (tasks != null) {
            tasks.delete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT_ITEM) {
            // Extract name value from result extras
            int position = data.getExtras().getInt("position");
            String originalTask = data.getExtras().getString("originalTask");
            taskAdapter.remove(taskAdapter.getItem(position));
            taskAdapter.insert((Task) data.getExtras().getSerializable("task"), position);
            updateDb(originalTask, (Task) data.getExtras().getSerializable("task"));
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_ITEM) {
            Task task = (Task) data.getExtras().getSerializable("task");
            taskAdapter.add(task);
            updateDb(task.getTask(), task);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Task task : arrayOfTasks) {
            updateDb(task.getTask(), task);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
