package com.example.mukulkarni.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.edit_activity);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.etEditItem);
        textView = (TextView) findViewById(R.id.tvEditItem);
        textView.setText(R.string.edit_activity_label);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //Display item's initial value
            String originalText = (String) bundle.get("text");
            editText.setText(originalText);

            //Set user's cursor in the text field is at the end of the current text value
            editText.setSelection(originalText.length());
        } else {
            editText.setText("");
        }
    }

    public void onSaveItem(View v) {
        int position = 0;
        String originalTask = "";
        EditText et = (EditText) findViewById(R.id.etEditItem);
        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        if (bundle != null) {
            position = (Integer) bundle.get("position");
            originalTask = (String) bundle.get("text");
        }
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("originalTask", originalTask);
        data.putExtra("text", et.getText().toString());
        data.putExtra("code", 200); // ints work too
        data.putExtra("position", position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
