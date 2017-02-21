package com.example.mukulkarni.simpletodo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mukulkarni.simpletodo.todo.Task;
import com.example.mukulkarni.simpletodo.todo.Task_Table;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mugdha Kulkarni
 */
public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 200;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        // readItems();
        readFromDb();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Add an item to the list
     *
     * @param v
     */
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeToDb(itemText);
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
                        i.putExtra("text", items.get(pos));
                        i.putExtra("position", pos);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        String itemDeleted = items.get(pos).toString();
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        deleteFromDb(itemDeleted);
                        return true;
                    }
                });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    //Read tasks from the Database
    private void readFromDb() {
        try {
            List<Task> taskList = SQLite.select().from(Task.class).queryList();
            for (Task task : taskList) {
                System.out.println("Task to be added is: " + task.getTask());
                items.add(task.getTask());
            }
        } catch (Exception e) {
            items = new ArrayList<String>();
        }
    }

    //Write the task to the Database
    private void writeToDb(String item) {
        Task task = new Task();
        task.setTask(item);
        task.save();
    }

    //Update database
    private void updateDb(String item, String newItem) {
        deleteFromDb(item);
        Task task = new Task();
        task.setTask(newItem);
        task.save();
    }

    private void deleteFromDb(String item) {
        Task tasks = SQLite.select().
                from(Task.class).
                where(Task_Table.task.is(item)).querySingle();
        if (tasks != null) {
            tasks.delete();
        }
    }


    private void writeItems() {
        File fielsDir = getFilesDir();
        File todoFile = new File(fielsDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editedItem = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position");
            String originalTask = data.getExtras().getString("originalTask");
            System.out.println("OriginalTaks is: " + originalTask);
            itemsAdapter.remove(itemsAdapter.getItem(position));
            itemsAdapter.insert(editedItem, position);
            updateDb(originalTask, editedItem);
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

        for (String item : items) {
            updateDb(item.toString(), item.toString());
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
