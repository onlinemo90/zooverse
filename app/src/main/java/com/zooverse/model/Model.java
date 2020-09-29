package com.zooverse.model;


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
		String[] columnNames = {"NAME"};
		Cursor cursor = database.query(
				"SPECIES",
				columnNames,
				null,
				null,
				null,
				null,
				null
		);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			list.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}
}
