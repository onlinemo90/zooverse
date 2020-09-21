package com.animalsoundsldn.model;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Model {
	
	private SQLiteDatabase database;
	private static Model instance;
	
	
	private Model() {
		DatabaseHelper dbHelper = new DatabaseHelper();
		this.database = dbHelper.getReadableDatabase();
	}
	
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	// add db entries for species to arraylist... currently only for returning the names
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
