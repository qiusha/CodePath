package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText et;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String item = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", 0);
        et = (EditText) findViewById(R.id.etEditItemContent);
        et.setText(item);
        et.setSelection(item.length());
    }

    public void saveItem(View v) {
        Intent data = new Intent();
        data.putExtra("item", et.getText().toString());
        data.putExtra("position", position); // ints work too
        setResult(RESULT_OK, data);
        this.finish();
    }
}
