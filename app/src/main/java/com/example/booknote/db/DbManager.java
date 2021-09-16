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

    public void deleteFromDb (int id){
        String selection = Constants._ID + "=" + id;
        sqLiteDatabase.delete(Constants.TABLE_NAME, selection, null);

    }

    public void updateDb (String title, String description, int id){
        String selection = Constants._ID + "=" + id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE, title);
        contentValues.put(Constants.DESCRIPTION, description);
        sqLiteDatabase.update(Constants.TABLE_NAME, contentValues, selection, null);

    }

    @SuppressLint("Range")
    public void selectFromDb(String title, OnDataReceived onDataReceived) {
        List<Note> noteList = new ArrayList<>();
        String selection = Constants.TITLE + " like ?";
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME, null, selection,
                new String[]{"%" + title + "%"}, null, null, null);
        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE)));
            note.setDescription(cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION)));
            note.setId(cursor.getInt(cursor.getColumnIndex(Constants._ID)));
            noteList.add(note);
        }
        cursor.close();
        onDataReceived.onReceived(noteList);
    }

    public void closeDb() {
        dbHelper.close();
    }

}
