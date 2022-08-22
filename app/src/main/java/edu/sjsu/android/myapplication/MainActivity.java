package edu.sjsu.android.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    MyDBHelper myDB;
    ArrayList<String> type;
    TypeAdapter mAdapter;

    ImageView emptyIcon;
    TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        addBtn = findViewById(R.id.addBtn);
        emptyIcon = findViewById(R.id.empty_icon);
        emptyLabel = findViewById(R.id.empty_label);

        myDB = new MyDBHelper(this);
        type = new ArrayList<>();

        readData();
        mAdapter = new TypeAdapter(this,this, type);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) recreate();
    }

    public void addBook(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void readData() {
        Cursor c = myDB.readType();
        if(c.getCount() != 0) {
            while(c.moveToNext()) {
                type.add(c.getString(0));
            }
            emptyIcon.setVisibility(View.GONE);
            emptyLabel.setVisibility(View.GONE);
        } else {
            emptyIcon.setVisibility(View.VISIBLE);
            emptyLabel.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteAll) {
            deleteConfirm();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure you want to remove all books from your book case?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper db = new MyDBHelper(MainActivity.this);
                MyDBHelper myDB = new MyDBHelper(MainActivity.this);
                myDB.deleteAll();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing when say no
            }
        });

        builder.create().show();
    }
}