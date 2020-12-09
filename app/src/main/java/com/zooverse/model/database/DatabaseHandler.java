package com.zooverse.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.zooverse.AssetManager;
import com.zooverse.MainApplication;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.zooverse.model.Individual;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteAssetHelper {
	private static final int DATABASE_VERSION = 26;
	
	private static final SimpleDateFormat ticketDateFormat = new SimpleDateFormat(DatabaseContract.TicketEntry.DATE_FORMAT);
	private static final SimpleDateFormat individualDobFormat = new SimpleDateFormat(DatabaseContract.IndividualEntry.DOB_FORMAT);
	
	private final SQLiteDatabase database;
	
	public DatabaseHandler() {
		super(MainApplication.getContext(), AssetManager.DATABASE_NAME, null, DATABASE_VERSION);
		this.setForcedUpgrade();
		
		this.database = getReadableDatabase();
	}
	
	// Ticket-----------------------------------------------------------
	public List<Ticket> getStoredTickets(Date afterDate) {
		String afterDateString = ticketDateFormat.format(afterDate);
		String[] columns = {
				DatabaseContract.TicketEntry.COLUMN_ZOO_ID,
				DatabaseContract.TicketEntry.COLUMN_DATE
		};
		String selection = DatabaseContract.TicketEntry.COLUMN_DATE + " >= ?";
		String[] selectionArgs = new String[]{afterDateString};
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
		String zooID;
		Date date;
		while (cursor.moveToNext()) {
			zooID = cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_ZOO_ID));
			try {
				date = ticketDateFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseContract.TicketEntry.COLUMN_DATE)));
			} catch (ParseException e) {
				continue;
			}
			ticketList.add(new Ticket(zooID, date));
		}
		cursor.close();
		return ticketList;
	}
	
	public void storeTicket(Ticket ticket) {
		ContentValues insertValues = new ContentValues();
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_ZOO_ID, ticket.getZooID());
		insertValues.put(DatabaseContract.TicketEntry.COLUMN_DATE, ticketDateFormat.format(ticket.getDate()));
		database.insert(
				DatabaseContract.TicketEntry.TABLE_NAME,
				null,
				insertValues
		);
	}
	
	// Species----------------------------------------------------------
	public Map<Integer, Species> getAllSpecies() {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.SpeciesEntry._ID,
						DatabaseContract.SpeciesEntry.COLUMN_NAME,
						DatabaseContract.SpeciesEntry.COLUMN_IMAGE
				},
				null, null, null, null,
				DatabaseContract.SpeciesEntry.COLUMN_NAME + " ASC"
		);
		Map<Integer, Species> speciesMap = new HashMap<>();
		int id;
		String name;
		byte[] imageBlob;
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.SpeciesEntry._ID));
			name = cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_NAME));
			imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_IMAGE));
			speciesMap.put(id, new Species(id, name, BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length)));
		}
		cursor.close();
		return speciesMap;
	}
	
	public List<Individual> getSpeciesIndividuals(Species species) {
		Cursor cursor = database.query(
				DatabaseContract.IndividualEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.IndividualEntry._ID,
						DatabaseContract.IndividualEntry.COLUMN_NAME,
						DatabaseContract.IndividualEntry.COLUMN_DOB,
						DatabaseContract.IndividualEntry.COLUMN_PLACE_OF_BIRTH,
						DatabaseContract.IndividualEntry.COLUMN_GENDER,
						DatabaseContract.IndividualEntry.COLUMN_WEIGHT,
						DatabaseContract.IndividualEntry.COLUMN_SIZE
				},
				DatabaseContract.IndividualEntry.COLUMN_SPECIES_ID + " = ?",
				new String[]{Integer.toString(species.getId())},
				null, null,
				DatabaseContract.IndividualEntry.COLUMN_NAME + " ASC"
		);
		
		List<Individual> individualList = new ArrayList<>();
		String name, placeOfBirth, gender, weight, size;
		Date dob = new Date();
		int id;
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.IndividualEntry._ID));
			name = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_NAME));
			try {
				dob = individualDobFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_DOB)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			placeOfBirth = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_PLACE_OF_BIRTH));
			gender = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_GENDER));
			weight = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_WEIGHT));
			size = cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_SIZE));
			
			individualList.add(new Individual(id, species, name, dob, placeOfBirth, gender, weight, size));
		}
		cursor.close();
		return individualList;
	}
	
	public String getSpeciesDescription(int speciesID) {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{DatabaseContract.SpeciesEntry.COLUMN_DESCRIPTION},
				DatabaseContract.SpeciesEntry._ID + " = ?",
				new String[]{String.valueOf(speciesID)},
				null, null, null
		);
		cursor.moveToNext();
		String description = cursor.getString(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_DESCRIPTION));
		cursor.close();
		return description;
	}
	
	public byte[] getSpeciesAudioDescription(int speciesID) {
		Cursor cursor = database.query(
				DatabaseContract.SpeciesEntry.TABLE_NAME,
				new String[]{DatabaseContract.SpeciesEntry.COLUMN_AUDIO},
				DatabaseContract.SpeciesEntry._ID + " = ?",
				new String[]{String.valueOf(speciesID)},
				null, null, null
		);
		cursor.moveToNext();
		byte[] audioBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.SpeciesEntry.COLUMN_AUDIO));
		cursor.close();
		return audioBlob;
	}
	
	public Bitmap getIndividualImage(int individualID) {
		Cursor cursor = database.query(
				DatabaseContract.IndividualEntry.TABLE_NAME,
				new String[]{
						DatabaseContract.IndividualEntry.COLUMN_IMAGE
				},
				DatabaseContract.IndividualEntry._ID + " = ?",
				new String[]{Integer.toString(individualID)},
				null, null, null
		);
		cursor.moveToNext();
		byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.IndividualEntry.COLUMN_IMAGE));
		cursor.close();
		return BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
	}
	
	public List<Pair<String, String>> getIndividualAttributes(int individualID) {
		List<Pair<String, String>> facts = new ArrayList<>();
		Cursor cursor = database.rawQuery(
				"select factsCat." + DatabaseContract.AnimalAttributeCategoryEntry.COLUMN_NAME + " , " +
						" indFacts." + DatabaseContract.IndividualInfoEntry.COLUMN_FACT +
						" from " + DatabaseContract.IndividualInfoEntry.TABLE_NAME + " as indFacts inner join " + DatabaseContract.AnimalAttributeCategoryEntry.TABLE_NAME + " as factsCat on " +
						" indFacts." + DatabaseContract.IndividualInfoEntry.COLUMN_CATEGORY_ID + " = factsCat." + DatabaseContract.AnimalAttributeCategoryEntry._ID +
						" where indFacts." + DatabaseContract.IndividualInfoEntry.COLUMN_INDIVIDUAL_ID + "=?",
				new String[]{Integer.toString(individualID)}
		);
		while (cursor.moveToNext()) {
			facts.add(new Pair<>(
							cursor.getString(cursor.getColumnIndex(DatabaseContract.AnimalAttributeCategoryEntry.COLUMN_NAME)),
							cursor.getString(cursor.getColumnIndex(DatabaseContract.IndividualInfoEntry.COLUMN_FACT))
					)
			);
		}
		cursor.close();
		return facts;
	}
}
