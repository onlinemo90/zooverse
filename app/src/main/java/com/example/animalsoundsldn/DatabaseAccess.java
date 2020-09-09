package com.example.animalsoundsldn;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    private DatabaseAccess(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    //return db instance
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

     //Open the database connection (read only)
    public void open() {
        this.database = dbHelper.getReadableDatabase();
    }

    //Close the database connection.
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

  //add db entries for species to arraylist... currently only for returning the names
    public ArrayList<String> getSpecies() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT name FROM species", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
