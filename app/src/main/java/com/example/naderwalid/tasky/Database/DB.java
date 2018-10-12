package com.example.naderwalid.tasky.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.naderwalid.tasky.Task;

import java.util.ArrayList;
import java.util.List;

public class DB {

    MySQLiteDatabase sql;
    SQLiteDatabase db;

    public DB(Context context) {
        sql = new MySQLiteDatabase(context,sql.DATABASE_NAME,null,1);
    }

    public long addTask(Task task){
        db = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MySQLiteDatabase.NAME,task.getName());
        values.put(MySQLiteDatabase.DATE,task.getDate());
        values.put(MySQLiteDatabase.PRIORITY_NUMBER,task.getPriority_number());
        long result =db.insert(MySQLiteDatabase.TABLE_NAME,null,values);
        db.close();
        return result;
    }

    public List<Task> getAllTasks(){
        db = sql.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteDatabase.TABLE_NAME,null);
        List<Task> databaseItems = new ArrayList<>();
        if(cursor.moveToFirst()){
            databaseItems.add(new Task(cursor.getString(1),
                    cursor.getString(2),cursor.getInt(3)));
            while (cursor.moveToNext()){
                databaseItems.add(new Task(cursor.getString(1),
                        cursor.getString(2),cursor.getInt(3)));
            }
        }
        cursor.close();
        db.close();
        return databaseItems;
    }

    public int updateTask(String oldName,String newName, int newNumber ) {
        // Get an instance of the database in (write) mode
        SQLiteDatabase db = sql.getWritableDatabase();

        // The data to insert into the table, the key here represents the column name
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("number", newNumber);

        // The where clause (condition)
        String where = "name = ?";
        String[] args = new String[]{String.valueOf(oldName)};

        // db.update will return the number of rows affected
        return db.update(sql.TABLE_NAME, values, where, args);
    }
    public int deleteTask(String name) {
        // Get an instance of the database in (write) mode
        SQLiteDatabase db = sql.getWritableDatabase();

        // The where clause (condition)
        String where = "name = ?";
        String[] args = new String[]{String.valueOf(name)};

        // db.delete will return the number of rows affected if a whereClause is passed in, 0 otherwise
        return db.delete(sql.TABLE_NAME, where, args);
    }

}
