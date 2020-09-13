package com.example.animalsoundsldn;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Model {

    private SQLiteDatabase database;
    private static Model instance;


    private Model(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.database = dbHelper.getReadableDatabase();
    }

    //return db instance
    public static Model getInstance(Context context) {
        if (instance == null) {
            instance = new Model(context);
        }
        return instance;
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
