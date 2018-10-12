package com.example.naderwalid.tasky.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteDatabase extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "tasky";
    static final String TABLE_NAME = "task";
    static final String ID = "_id";
    static final String NAME = "name";
    static final String PRIORITY_NUMBER = "number";
    static final String DATE = "date";
    String query = "CREATE TABLE "+TABLE_NAME+" (_id integer primary key autoincrement not null,"+ NAME+" text, "+DATE+" text, "+PRIORITY_NUMBER+" integer)";

    public MySQLiteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
