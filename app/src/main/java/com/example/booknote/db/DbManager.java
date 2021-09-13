package com.example.booknote.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.booknote.adapter.Note;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String title, String description) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.TITLE, title);
        cv.put(Constants.DESCRIPTION, description);
        sqLiteDatabase.insert(Constants.TABLE_NAME, null, cv);

    }

    @SuppressLint("Range")
    public List<Note> selectFromDb() {
        List<Note> noteList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE)));
            note.setDescription(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION)));
            noteList.add(note);
        }
        cursor.close();
        return noteList;
    }

    public void closeDb() {
        dbHelper.close();
    }

}
