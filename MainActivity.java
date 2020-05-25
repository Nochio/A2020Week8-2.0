package com.example.week8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.HashSet;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.myListView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.week8", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes.add("Example note");
        } else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        button = findViewById(R.id.addItemButton);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    notes.remove(position);
                                    arrayAdapter.notifyDataSetChanged();

                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.week8", Context.MODE_PRIVATE);

                                    HashSet<String> set = new HashSet(MainActivity.notes);

                                    sharedPreferences.edit().putStringSet("notes", set).apply();
                                }
                            }
                        )
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }

    public void addItemToList (View view){
        addNewNote();
    }

    public void addNewNote() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add(" Item " + notes.size());
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }


}
