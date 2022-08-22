package edu.sjsu.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class AddActivity extends AppCompatActivity {

    private EditText input_title, input_author, input_pages;
    private AutoCompleteTextView input_type;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        String[] types = this.getResources().getStringArray(R.array.book_type);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_items, types);

        input_title = findViewById(R.id.book_title);
        input_author = findViewById(R.id.book_author);
        input_pages = findViewById(R.id.book_pages);

        input_type = findViewById(R.id.autoCompleteTextView);
        input_type.setAdapter(adapter);

        saveBtn = findViewById(R.id.saveBtn);
    }

    public void saveBook(View view) {
        MyDBHelper myDB = new MyDBHelper(this);
        myDB.add(input_title.getText().toString().trim(),
                input_author.getText().toString().trim(),
                Integer.valueOf(input_pages.getText().toString().trim()),
                input_type.getText().toString().trim());
    }
}