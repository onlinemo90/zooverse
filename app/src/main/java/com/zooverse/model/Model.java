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
	private List<Species> speciesList;
	
	private Model() {
		DatabaseHelper dbHelper = new DatabaseHelper();
		dbHelper.setForcedUpgrade();
		this.database = dbHelper.getReadableDatabase();
		this.storedTickets = this.initStoredTickets();
		this.speciesList = this.initSpeciesList();
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
	
	private List<Species> initSpeciesList() {
		Cursor cursor = database.query(
				"SPECIES",
				new String[]{"NAME", "DESCRIPTION", "IMAGE", "AUDIO"},
				null, null, null, null,
				"NAME ASC"
		);
		List<Species> speciesList = new ArrayList<>();
		while (cursor.moveToNext()) {
			speciesList.add(new Species(
							cursor.getString(cursor.getColumnIndex("NAME")),
							cursor.getString(cursor.getColumnIndex("DESCRIPTION")),
							cursor.getBlob(cursor.getColumnIndex("IMAGE")),
							cursor.getBlob(cursor.getColumnIndex("AUDIO"))
					)
			);
		}
		return speciesList;
	}
	
	public List<Ticket> getStoredTickets() {
		return this.storedTickets;
	}
	
	public List<Species> getSpeciesList() {
		return this.speciesList;
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
		this.storedTickets.add(0, ticket);
	}
	
}
