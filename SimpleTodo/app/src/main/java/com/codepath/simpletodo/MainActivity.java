package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItem();
        lvItems = (ListView) findViewById(R.id.lvItems);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                String item = (String)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v,
                                           int position, long l) {
                String item = (String)adapterView.getItemAtPosition(position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }

        });

        //items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Item", Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String item = data.getExtras().getString("item");
            int position = data.getExtras().getInt("position", 0);
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            writeItem();
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItem();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });
    }

    private void readItem() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo2.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItem() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo2.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
