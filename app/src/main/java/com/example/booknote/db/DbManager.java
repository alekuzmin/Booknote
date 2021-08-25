package com.example.booknote.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public List<String> selectFromDb() {
        List<String> tempList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE));
            tempList.add(title);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb() {
        dbHelper.close();
    }

}
