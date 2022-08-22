package edu.sjsu.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private TextView update_title, update_author, update_pages;
    private AutoCompleteTextView update_type;
    private Button updateBtn, deleteBtn;
    String id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        String[] types = this.getResources().getStringArray(R.array.book_type);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_items, types);

        update_title = findViewById(R.id.update_title);
        update_author = findViewById(R.id.update_author);
        update_pages = findViewById(R.id.update_pages);

        update_type = findViewById(R.id.autoCompleteTextView);
        update_type.setAdapter(adapter);

        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        updateData();
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle(title);
        }
    }

    public void updateBook(View view) {
        MyDBHelper db = new MyDBHelper(this);
        db.update(id,
                update_title.getText().toString().trim(),
                update_author.getText().toString().trim(),
                Integer.valueOf(update_pages.getText().toString().trim()),
                update_type.getText().toString().trim());
        finish();
    }

    public void deleteBook(View view) {
        deleteConfirm();
    }

    public void deleteConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to remove " + title + " from your book case?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper db = new MyDBHelper(UpdateActivity.this);
                db.delete(id);
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

    public void updateData() {
        if(getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");

            update_title.setText(title);
            update_author.setText(getIntent().getStringExtra("author"));
            update_pages.setText(getIntent().getStringExtra("pages"));
            update_type.setText(getIntent().getStringExtra("type"));
        } else Toast.makeText(this, "This book has nothing to update", Toast.LENGTH_SHORT).show();
    }
}