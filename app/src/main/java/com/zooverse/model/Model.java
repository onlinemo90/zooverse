package com.zooverse.model;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Model {
	
	private SQLiteDatabase database;
	private static Model instance;
	
	private Model() {
		DatabaseHelper dbHelper = new DatabaseHelper();
		dbHelper.setForcedUpgrade();
		this.database = dbHelper.getReadableDatabase();
	}
	
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	public Cursor getAllSpecies(String searchCriterion) {
		return database.query(
			   "SPECIES",
			   null,
			   "name LIKE '%"+searchCriterion+"%'",
			   null,
			   null,
			   null,
			   null
	   );
	}
}
