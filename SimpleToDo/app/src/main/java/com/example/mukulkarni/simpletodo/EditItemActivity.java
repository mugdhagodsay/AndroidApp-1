package com.example.mukulkarni.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Item");
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.etEditItem);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            String originalText = (String) bundle.get("text");
            editText.setText(originalText);
            editText.setSelection(originalText.length());
        } else {
            editText.setText("");
        }

    }

    public void onSaveItem(View v) {
        int position = 0;
        EditText et = (EditText) findViewById(R.id.etEditItem);
        //String text = getIntent().getExtras().toString();
        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        if(bundle !=null){
            position = (Integer) bundle.get("position");
        }
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("text", et.getText().toString());
        data.putExtra("code", 200); // ints work too
        data.putExtra("position", position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
