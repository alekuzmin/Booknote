package com.example.booknote.db;

public class Constants {
    public static final String TABLE_NAME = "main_table";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DB_NAME = "main_db.db";
    public static final int DB_VERSION = 1;
    public static final String SQL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + TITLE + " TEXT, " +
            DESCRIPTION + " TEXT)";
    public static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
