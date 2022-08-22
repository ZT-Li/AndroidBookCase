package edu.sjsu.android.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "BookCase.db";
    private static final int DB_VERSION = 1;

    private String TABLE_NAME = "book_case";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_TYPE = "type";

    public MyDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_PAGES + " INTEGER, " +
                        COLUMN_TYPE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add(String title, String author, int pages, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_TYPE, type);

        long res = db.insert(TABLE_NAME, null, cv);
        if(res == -1) {
            Toast.makeText(context, "fail to save book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Book saved successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        if(db!=null)
            c = db.rawQuery(query, null);

        return c;
    }

    public Cursor readAllInOneType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] column = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_PAGES};

        Cursor c = null;
        if(db != null)
            c = db.query(TABLE_NAME, column, "type=?", new String[]{type},null,null,null,null);

        return c;
    }

    public Cursor readType() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if(db != null)
            c = db.query(true,TABLE_NAME,new String[]{COLUMN_TYPE},null,null,null,null,null,null);
        return c;
    }

    public void update(String row_id, String title, String author, int pages, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_TYPE, type);

        long res = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(res == -1) {
            Toast.makeText(context, "fail to update book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Book update successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if(res == -1) {
            Toast.makeText(context, "fail to remove book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "All Books removed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void deleteAllFromOneType(String type) {
        SQLiteDatabase db = getWritableDatabase();
        long res = db.delete(TABLE_NAME, "type=?", new String[]{type});

        if(res == -1) {
            Toast.makeText(context, "fail to remove book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, type + " book removed!", Toast.LENGTH_SHORT).show();
        }
    }
}
