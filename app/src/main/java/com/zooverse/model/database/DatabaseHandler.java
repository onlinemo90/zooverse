package com.zooverse.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zooverse.AssetManager;
import com.zooverse.MainApplication;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteAssetHelper {
	private static final int DATABASE_VERSION = 11;
	
	private final SQLiteDatabase database;
	
	public DatabaseHandler() {
		super(MainApplication.getContext(), AssetManager.DATABASE_NAME, null, DATABASE_VERSION);
		this.setForcedUpgrade();
		
		this.database = getReadableDatabase();
	}
	
	public List<Ticket> getStoredTickets(String afterDate) {
		String[] columns = {
				DatabaseContract.TicketEntry.COLUMN_ZOO_ID,
				DatabaseContract.TicketEntry.COLUMN_DATE
		};
		String selection = DatabaseContract.TicketEntry.COLUMN_DATE + " >= ?";
		String[] selectionArgs = new String[]{afterDate};
		Cursor cursor = database.query(
				DatabaseContract.TicketEntry.TABLE_NAME,
				columns,
				selection,
				selectionArgs,
				null,
				null,
				DatabaseContract.TicketEntry.COLUMN_DATE + " ASC"
		);
		
		// Parse data into List
		List<Ticket> ticketList = new ArrayList<>();
		while (cursor.moveToNext()) {
			Ticket tmpTicket = new Ticket(
					cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_ZOO_ID)),
					cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_DATE))
			);
			if (tmpTicket.isValid()) {
				ticketList.add(tmpTicket);
			}
		}
		cursor.close();
		return ticketList;
	}
	
	public void storeTicket(Ticket ticket) {
		ContentValues insertValues = new ContentValues();
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_ZOO_ID, ticket.getZooID());
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_DATE, ticket.getFormattedDate());
		database.insert(
				DatabaseContract.TicketEntry.TABLE_NAME,
				null,
				insertValues
		);
	}
	
	public List<Species> getAllSpecies() {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.SpeciesEntry._ID,
						DatabaseContract.SpeciesEntry.COLUMN_NAME,
						DatabaseContract.SpeciesEntry.COLUMN_DESCRIPTION
				},
				null, null, null, null,
				DatabaseContract.SpeciesEntry.COLUMN_NAME + " ASC"
		);
		List<Species> speciesList = new ArrayList<>();
		while (cursor.moveToNext()) {
			speciesList.add(new Species(
							cursor.getInt(cursor.getColumnIndex(DatabaseContract.SpeciesEntry._ID)),
							cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_NAME)),
							cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_DESCRIPTION))
					)
			);
		}
		cursor.close();
		return speciesList;
	}
	
	public byte[] getSpeciesImage(int speciesID) {
		return this.getBlobBySpeciesId(speciesID, DatabaseContract.SpeciesEntry.COLUMN_IMAGE);
	}
	
	public byte[] getSpeciesAudioDescription(int speciesID) {
		return this.getBlobBySpeciesId(speciesID, DatabaseContract.SpeciesEntry.COLUMN_AUDIO);
	}
	
	private byte[] getBlobBySpeciesId(int id, String blobColumnName) {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{blobColumnName},
				DatabaseContract.SpeciesEntry._ID + " = ?",
				new String[]{String.valueOf(id)},
				null, null, null
		);
		cursor.moveToNext();
		byte[] blob = cursor.getBlob(cursor.getColumnIndex(blobColumnName));
		cursor.close();
		return blob;
	}
}
