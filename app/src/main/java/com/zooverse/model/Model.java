package com.zooverse.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Model {
	private static Model instance;
	private SQLiteDatabase database;
	
	private List<Ticket> storedTickets;
	
	private Model() {
		DatabaseHelper dbHelper = new DatabaseHelper();
		dbHelper.setForcedUpgrade();
		this.database = dbHelper.getReadableDatabase();
		this.storedTickets = this.initStoredTickets();
	}
	
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	private List<Ticket> initStoredTickets() {
		// Get tickets from DB
		String[] columns = {"ZOO_ID", "DATE"};
		String selection = "DATE >= ?";
		String[] selectionArgs = new String[]{Ticket.getTodayFormattedDate()};
		Cursor cursor = database.query(
				"TICKET",
				columns,
				selection,
				selectionArgs,
				null,
				null,
				"DATE ASC"
		);
		
		// Parse data into List
		List<Ticket> ticketList = new ArrayList<>();
		while (cursor.moveToNext()) {
			Ticket tmpTicket = new Ticket(
					cursor.getString(cursor.getColumnIndex("ZOO_ID")),
					cursor.getString(cursor.getColumnIndex("DATE"))
			);
			if (tmpTicket.isValid()) {
				ticketList.add(tmpTicket);
			}
		}
		cursor.close();
		return ticketList;
	}
	
	public List<Ticket> getStoredTickets() {
		return this.storedTickets;
	}
	
	public void storeTicket(Ticket ticket) {
		ContentValues insertValues = new ContentValues();
		insertValues.put("ZOO_ID", ticket.getZooID());
		insertValues.put("DATE", ticket.getFormattedDate());
		database.insert(
				"TICKET",
				null,
				insertValues
		);
		this.storedTickets.add(ticket);
	}
	
	public Cursor getAllSpecies(String searchCriterion) {
		return database.query(
				"SPECIES",
				null,
				"name LIKE '%" + searchCriterion + "%'", //TODO: VULNERABLE TO SQL INJECTION!!!
				null,
				null,
				null,
				null
		);
	}
	
}
